package rbtree;

import static rbtree.Node.ColorTreeNode.BLACK;
import static rbtree.Node.ColorTreeNode.RED;

public class RedBlackTree<T extends Comparable<T>> {
    private Node.ColorTreeNode<T> root;

    public RedBlackTree() {
        this.root = null;
    }

    public RedBlackTree(Node.ColorTreeNode<T> node) {
        this.root =  new Node.ColorTreeNode<>(node.key, node.left, node.right, BLACK);
    }

    public RedBlackTree(T v) {
        this.root = new Node.ColorTreeNode<>(v, null, null, BLACK);
    }

    // return null if key not exists
    public Node.ColorTreeNode<T> find(T key) {
        Node.ColorTreeNode<T> p = root;
        while (p != null) {
            int cmp = p.key.compareTo(key);
            if (cmp == 0) {
                return p;
            } else if (cmp > 0) {
                p = (Node.ColorTreeNode<T>) p.left;
            } else {
                p = (Node.ColorTreeNode<T>) p.right;
            }
        }

        return null;
    }

    public void insert(T key) {
        if (root == null) {
            root = new Node.ColorTreeNode<>(key, null, null, BLACK);
            return;
        }

        Node.ColorTreeNode<T> parent = root;
        Node.ColorTreeNode<T> newNode;
        while (true) {
            int cmp = parent.key.compareTo(key);
            if (cmp == 0) {
                return;
            } else if (cmp > 0) {
                // to left
                if (parent.left == null) {
                    parent.left = (newNode = new Node.ColorTreeNode<>(key, null, null, RED));
                    break;
                } else {
                    parent = (Node.ColorTreeNode<T>) parent.left;
                }
            } else {
                // to right
                if (parent.right == null) {
                    parent.right = (newNode = new Node.ColorTreeNode<>(key, null, null, RED));
                    break;
                } else {
                    parent = (Node.ColorTreeNode<T>) parent.right;
                }
            }
        }

        // double red problem
        if (parent.getColor() == RED) {
            rebalanceInsert(newNode);
        }
    }

    private void rebalanceInsert(Node.ColorTreeNode<T> child) {
        Node.ColorTreeNode<T> gr_grandParent, grandParent, parent;
        parent = getParent(child);
        grandParent = getParent(parent);
        gr_grandParent = getParent(grandParent);

        // sibling of parent
        Node.ColorTreeNode<T> s = getSibling(grandParent, parent);

        // Case 1: The Sibling s of y is Black
        // refer to Goodrich p531 Figure 11.32
        // confusion: s can only be null? if it is black, then the original subtree
        // black height won't be equal
        // ref: Figure 11.35 (p), during recursion, s may not be null due to lower level re-color
        while (s == null || s.getColor() == BLACK) {
        // while (s == null) {
            int cmp1 = grandParent.compareTo(parent);
            int cmp2 = parent.compareTo(child);
            if (cmp1 > 0 && cmp2 > 0) {
                parent.flipColor();
                grandParent.flipColor();
                grandParent.left = parent.right;
                parent.right = grandParent;
                if (gr_grandParent == null) {
                    root = parent;
                } else {
                    if (gr_grandParent.left == grandParent) {
                        gr_grandParent.left = parent;
                    } else {
                        gr_grandParent.right = parent;
                    }
                }
                break;
            }
            if (cmp1 > 0 && cmp2 < 0) {
                child.flipColor();
                grandParent.flipColor();
                parent.right = child.left;
                grandParent.left = child.right;
                child.left = parent;
                child.right = grandParent;
                if (gr_grandParent == null) {
                    root = child;
                } else {
                    if (gr_grandParent.left == grandParent) {
                        gr_grandParent.left = child;
                    } else {
                        gr_grandParent.right = child;
                    }
                }
                break;
            }
            if (cmp1 < 0 && cmp2 < 0) {
                parent.flipColor();
                grandParent.flipColor();
                grandParent.right = parent.left;
                parent.left = grandParent;
                if (gr_grandParent == null) {
                    root = parent;
                } else {
                    if (gr_grandParent.left == grandParent) {
                        gr_grandParent.left = parent;
                    } else {
                        gr_grandParent.right = parent;
                    }
                }
                break;
            }
            if (cmp1 < 0 && cmp2 > 0) {
                child.flipColor();
                grandParent.flipColor();
                grandParent.right = child.left;
                parent.left = child.right;
                child.left = grandParent;
                child.right = parent;
                if (gr_grandParent == null) {
                    root = child;
                } else {
                    if (gr_grandParent.left == grandParent) {
                        gr_grandParent.left = child;
                    } else {
                        gr_grandParent.right = child;
                    }
                }
                break;
            }
            break;
        }

        // Case 2: The Sibling s of y is Red
        while (s != null && s.getColor() == RED) {
            parent.flipColor();
            s.flipColor();

            if (grandParent != root) {
                // change to red
                grandParent.flipColor();
                if (gr_grandParent.getColor() == RED) {
                    rebalanceInsert(grandParent);
                }
            }
            break;
        }
    }

    // false only key not exists
    public void delete(T key) {
        Node.ColorTreeNode<T> parent, node;
        parent = null;
        node = root;
        while (node != null) {
            int cmp = node.key.compareTo(key);
            if (cmp == 0) {
                break;
            }
            parent = node;
            if (cmp > 0) {
                node = (Node.ColorTreeNode<T>) node.left;
            } else {
                node = (Node.ColorTreeNode<T>) node.right;
            }
        }

        // not exists
        if (node == null) {
            return;
        }

        // leaf node
        if (node.left == null && node.right == null) {
            // single node in tree, node == root
            if (parent == null) {
                root = null;
                return;
            } else {
                int cmp = node.key.compareTo(parent.key);
                if (cmp < 0) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                return;
            }
        }

        // non leaf node
        Node.ColorTreeNode<T> swapNode = null, parentOfSwapNode = node;
        if (node.left != null) {
            // rightmost node of left subtree
            swapNode = (Node.ColorTreeNode<T>) node.left;
            while (swapNode.right != null) {
                parentOfSwapNode = swapNode;
                swapNode = (Node.ColorTreeNode<T>) swapNode.right;
            }
            if (parentOfSwapNode != node) {
                parentOfSwapNode.right = swapNode.left;
            } else {
                node.left = null;
            }
        } else {
            // leftmost node of right subtree
            swapNode = (Node.ColorTreeNode<T>) node.right;
            while (swapNode.left != null) {
                parentOfSwapNode = swapNode;
                swapNode = (Node.ColorTreeNode<T>) swapNode.left;
            }
            if (parentOfSwapNode != node) {
                parentOfSwapNode.left = swapNode.right;
            } else {
                node.right = null;
            }
        }
        node.key = swapNode.key;

        rebalanceDelete(null, null);
    }

    private void rebalanceDelete(Node.ColorTreeNode<T> parent, Node.ColorTreeNode<T> child) {
        // Case 1: The Sibling s of y is Black
        // Case 2: The Sibling s of y is Red
    }

    private Node.ColorTreeNode<T> getParent(Node.ColorTreeNode<T> child) {
        if (child == null || child == root) {
            return null;
        }

        Node.ColorTreeNode<T> parent = root;
        while (parent != null) {
            if (parent.left == child || parent.right == child) {
                return parent;
            }
            int cmp = child.getKey().compareTo(parent.getKey());
            if (cmp < 0) {
                parent = (Node.ColorTreeNode<T>) parent.left;
            } else {
                parent = (Node.ColorTreeNode<T>) parent.right;
            }
        }

        return null;
    }

    private Node.ColorTreeNode<T> getSibling(Node.ColorTreeNode<T> grandParent, Node.ColorTreeNode<T> parent) {
        if (grandParent.left == parent) {
            return (Node.ColorTreeNode<T>) grandParent.right;
        } else {
            return (Node.ColorTreeNode<T>) grandParent.left;
        }
    }

    public void printTree(Node.ColorTreeNode<T> tree) {
        System.out.println("----Print tree----");
        System.out.println("------------------");
        BTreePrinter.printNode(tree);
        System.out.println();
    }

    private static void insertExample() {
        // Goodrich p533 6th
        // Figure 11.34
        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.insert(4);
        bst.printTree(bst.root);
        bst.insert(7);
        bst.printTree(bst.root);
        bst.insert(12);
        bst.printTree(bst.root);
        bst.insert(15);
        bst.printTree(bst.root);
        bst.insert(3);
        bst.printTree(bst.root);
        bst.insert(5);
        bst.printTree(bst.root);
        bst.insert(14);
        bst.printTree(bst.root);
        bst.insert(18);
        bst.printTree(bst.root);

        // Figure 11.35
        bst.insert(16);
        bst.printTree(bst.root);
        bst.insert(17);
        bst.printTree(bst.root);
    }
    public static void main(String[] args) {
        insertExample();

        // Node.ColorTreeNode<Integer> node = bst.find(8);

//        bst.delete(15);
//        bst.printTree(bst.root);
//        bst.delete(10);
//        bst.printTree(bst.root);
//        bst.delete(13);
//        bst.printTree(bst.root);
//        bst.delete(18);
//        bst.printTree(bst.root);
    }
}
