package rbtree;

import static rbtree.Node.BLACK;
import static rbtree.Node.RED;

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;

    public RedBlackTree() {
        this.root = null;
    }

    // return null if key not exists
    public Node<T> find(T key) {
        Node<T> p = root;
        while (p != null) {
            int cmp = p.key.compareTo(key);
            if (cmp == 0) {
                return p;
            } else if (cmp > 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        return null;
    }

    public void insert(T key) {
        if (root == null) {
            root = new Node<>(key, null, null, null, BLACK);
            return;
        }

        Node<T> parent = root;
        Node<T> newNode;
        while (true) {
            int cmp = parent.key.compareTo(key);
            if (cmp == 0) {
                return;
            } else if (cmp > 0) {
                // to left
                if (parent.left == null) {
                    parent.left = (newNode = new Node<>(key, null, null, parent, RED));
                    break;
                } else {
                    parent = parent.left;
                }
            } else {
                // to right
                if (parent.right == null) {
                    parent.right = (newNode = new Node<>(key, null, null, parent, RED));
                    break;
                } else {
                    parent = parent.right;
                }
            }
        }

        // double red problem
        if (parent.getColor() == RED) {
            fixInsert(newNode);
        }
    }

    public void delete(T key) {
        Node<T> node;
        node = root;
        while (node != null) {
            int cmp = node.key.compareTo(key);
            if (cmp == 0) {
                break;
            }
            if (cmp > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        // not exists
        if (node == null) {
            return;
        }

        // if black, then fix delete from this node
        Node<T> movedUpNode = null;
        boolean delNodeColor;

        // leaf node or one child only node
        if (node.left == null || node.right == null) {
            // single node in tree
            delNodeColor = node.getColor();
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
       } else {
            // non leaf node, swap with left most node, then delete the swap node
            // rightmost node of left subtree
            Node<T> swapNode = node.left;
            while (swapNode.right != null) {
                swapNode = swapNode.right;
            }

            // swap
            node.key = swapNode.key;

            // L17-Red-Black-Trees-clean.pptx p25 case1,2
            // red node or black node with single red left child
            delNodeColor = swapNode.getColor();
            movedUpNode = deleteNodeWithZeroOrOneChild(swapNode);
        }

        if (delNodeColor == BLACK) {
            // simple case: black node with single red left or right node
            // refer to: L17-Red-Black-Trees-clean.pptx p25 the second case
            if (movedUpNode.color == RED) {
                // only flip color is needed
                movedUpNode.flipColor();
            } else {
                // deleted black leaf node, fix needed
                // movedUpNode instanceof Node.ExternalNode.class
                fixDelete(movedUpNode);
                // movedUpNode.getClass() == Node.ExternalNode.class
                resetChild(movedUpNode.parent, movedUpNode, null);
            }
        }
    }

    private void fixDelete(Node<T> node) {
        if (node == root) {
            root.setColor(BLACK);
            return;
        }

        // refer to: L17-Red-Black-Trees-clean.pptx p27
        Node<T> a = node.parent;
        Node<T> b = getSibling(a, node);
        Node<T> c1, c2, c;

        // case 1 => parent: red
        if (a.getColor() == RED && b != null) {
            c1 = b.left;
            c2 = b.right;

            // case1.1 some children of sibling (b) of node is red
            if ((c1 != null && c1.getColor() == RED) || (c2 != null && c2.getColor() == RED)) {
                case1_1(a, node);
            } else {
                // case1.2 both children of sibling (b) of node are black
                case1_2(a, node);
            }
            return;
        }

        // case 2 => parent: black
        if (a.getColor() == BLACK && b != null) {
            // case2.1 sibling b is red
            if (b.getColor() == RED) {
                if (a.right == node) {
                    // b.right won't be null in this case, otherwise it would violate black height
                    c = b.right;
                    Node<T> d1 = c.left;
                    Node<T> d2 = c.right;

                    // case2.1.1 some grandchild of b is red
                    if ((d1 != null && d1.getColor() == RED) || (d2 != null && d2.getColor() == RED)) {
                        case_2_1_1(a, node);
                    } else {
                        // case2.1.2 both grandchildren of b are black
                        case_2_1_2(a, node);
                    }
                } else {
                    // b.left won't be null in this case, otherwise it would violate black height
                    c = b.left;
                    Node<T> d1 = c.left;
                    Node<T> d2 = c.right;

                    // case2.1.1 some grandchild of b is red
                    if ((d1 != null && d1.getColor() == RED) || (d2 != null && d2.getColor() == RED)) {
                        case_2_1_1(a, node);
                    } else {
                        // case2.1.2 both grandchildren of b are black
                        case_2_1_2(a, node);
                    }
                }
                return;
            }

            // case2.2 sibling b is black
            if (b.getColor() == BLACK) {
                c1 = b.left;
                c2 = b.right;

                // case2.2.1 some child of b is red
                if ((c1 != null && c1.getColor() == RED) || (c2 != null && c2.getColor() == RED)) {
                    case_2_2_1(a, node);
                } else {
                    // case2.2.2 both children of b are black
                    case_2_2_2(a, node);
                    fixDelete(node.parent);
                }
            }
        }
    }
    private void case1_1(Node<T> a, Node<T> node) {
        Node<T> b = getSibling(a, node);
        Node<T> g = getParent(a);
        Node<T> c1, c2;
        c1 = b.left;
        c2 = b.right;
        // on ppt is only a single case, have to cope with mirror case in else
        if (a.right == node) {
            if (c2 != null) {
                b.right = c2.left;
                a.left = c2.right;
                a.flipColor();
                if (g.left == a) {
                    g.left = c2;
                } else {
                    g.right = c2;
                }
                c2.left = b;
                c2.right = a;
            } else {
                a.left = b.right;
                a.flipColor();
                c1.flipColor();
                b.flipColor();
                if (g.left == a) {
                    g.left = b;
                } else {
                    g.right = b;
                }
                b.right = a;
            }
        } else {
            if (c2 != null) {
                a.right = b.left;
                a.flipColor();
                b.left = a;
                b.flipColor();
                if (g.left == a) {
                    g.left = b;
                } else {
                    g.right = b;
                }
                c2.flipColor();
            } else {
                a.right = c1.left;
                a.flipColor();
                b.left = c1.right;
                if (g.left == a) {
                    g.left = c1;
                } else {
                    g.right = c1;
                }
                c1.left = a;
                c1.right = b;
            }
        }
    }
    // case1.2 both children of sibling (b) of node are black
    private void case1_2(Node<T> a, Node<T> node) {
        Node<T> b = getSibling(a, node);
        a.flipColor();
        b.flipColor();
    }
    // case2.1.1 some grandchild of b is red
    private void case_2_1_1(Node<T> a, Node<T> node) {
        Node<T> b = getSibling(a, node);
        Node<T> g = getParent(a);
        Node<T> c;
        c = b.right;
        Node<T> d1 = c.left;
        Node<T> d2 = c.right;
        if (a.right == node) {
            if (d1 != null) {
                b.right = d1;
                d1.flipColor();
                a.left = c.right;
                c.left = b;
                c.right = a;
                // a is root
                if (g == null) {
                    root = c;
                } else {
                    if (g.left == a) {
                       g.left = c;
                    } else {
                        g.right = c;
                    }
                }
            } else {
                c.right = d2.left;
                a.left = d2.right;
                d2.left = b;
                d2.right = a;
                d2.flipColor();
                if (g == null) {
                    root = d2;
                } else {
                    if (g.left == a) {
                        g.left = d2;
                    } else {
                        g.right = d2;
                    }
                }
            }
        } else {
            // mirror case
            if (d2 != null) {
                a.right = c.left;
                b.left = d2;
                d2.flipColor();
                c.left = a;
                c.right = b;
                // a is root
                if (g == null) {
                    root = c;
                } else {
                    if (g.left == a) {
                        g.left = c;
                    } else {
                        g.right = c;
                    }
                }
            } else {
                a.right = d1.left;
                c.left = d1.right;
                d1.flipColor();
                d1.left = a;
                d1.right = b;
                if (g == null) {
                    root = d1;
                } else {
                    if (g.left == a) {
                        g.left = d1;
                    } else {
                        g.right = d1;
                    }
                }
            }
        }
    }
    // case2.1.2 both grandchildren of b are black
    private void case_2_1_2(Node<T> a, Node<T> node) {
        Node<T> b = getSibling(a, node);
        Node<T> g = getParent(a);
        Node<T> c;
        if (a.right == node) {
            c = b.right;
            a.left = c;
            c.flipColor();
            b.right = a;
            b.flipColor();
            if (a == root) {
                root = b;
            } else {
                if (g.left == a) {
                    g.left = b;
                } else {
                    g.right = b;
                }
            }
        } else {
            // mirror case
            c = b.left;
            a.right = c;
            c.flipColor();
            b.left = a;
            b.flipColor();
            if (a == root) {
                root = b;
            } else {
                if (g.left == a) {
                    g.left = b;
                } else {
                    g.right = b;
                }
            }
        }
    }
    // case2.2.1 some child of b is red
    private void case_2_2_1(Node<T> a, Node<T> node) {
        Node<T> g = getParent(a);
        Node<T> c = getSibling(a, node);
        Node<T> d1 = c.left;
        Node<T> d2 = c.right;
        if (a.right == node) {
            if (d2 != null) {
                c.right = d2.left;
                a.left = d2.right;
                d2.left = c;
                d2.right = a;
                d2.flipColor();
                if (root == a) {
                    root = d2;
                } else {
                    if (g.left == a) {
                        g.left = d2;
                    } else {
                        g.right = d2;
                    }
                }
            } else {
                a.left = c.right;
                c.right = a;
                d1.flipColor();
                if (root == a) {
                    root = c;
                } else {
                    if (g.left == a) {
                        g.left = c;
                    } else {
                        g.right = c;
                    }
                }
            }
        } else {
            // mirror case
            if (d1 != null) {
                a.right = d1.left;
                c.left = d1.right;
                d1.left = a;
                d1.right = c;
                d1.flipColor();
                if (root == a) {
                    root = d1;
                } else {
                    if (g.left == a) {
                        g.left = d1;
                    } else {
                        g.right = d1;
                    }
                }
            } else {
                a.right = c.left;
                c.left = a;
                d2.flipColor();
                if (root == a) {
                    root = c;
                } else {
                    if (g.left == a) {
                        g.left = c;
                    } else {
                        g.right = c;
                    }
                }
            }
        }
    }
    // case2.2.2 both children of b are black
    private void case_2_2_2(Node<T> a, Node<T> node) {
        if (a.right == node) {
            a.left.flipColor();
        } else {
            a.right.flipColor();
        }
    }
    
    private Node<T>  deleteNodeWithZeroOrOneChild(Node<T> node) {
        Node<T> parent = node.parent;
        if (node.left != null) {
            resetChild(parent, node, node.left);
            return node.left;
        } else if (node.right != null) {
            resetChild(parent, node, node.right);
            return node.right;
        } else {
            Node<T> child = node.color == RED ? null : new Node.ExternalNode<>(null, parent);
            resetChild(parent, node, child);
            return child;
        }
    }

    private Node<T> getParent(Node<T> child) {
        if (child == null || child == root) {
            return null;
        }

        Node<T> parent = root;
        while (parent != null) {
            if (parent.left == child || parent.right == child) {
                return parent;
            }
            int cmp = child.getKey().compareTo(parent.getKey());
            if (cmp < 0) {
                parent = parent.left;
            } else {
                parent = parent.right;
            }
        }

        return null;
    }

    private Node<T> getSiblingOfParent(Node<T> grandParent, Node<T> parent) {
        if (grandParent.left == parent) {
            return grandParent.right;
        } else {
            return grandParent.left;
        }
    }

    private Node<T> getSibling(Node<T> parent, Node<T> node) {
        if (parent.left == node) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    private void resetChild(Node<T> parent, Node<T> oldNode, Node<T> newNode) {
        if (parent == null) {
            root = newNode;
        } else if (parent.left == oldNode) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        if (newNode != null) {
            newNode.parent = parent;
        }
    }

    private void fixInsert(Node<T> child) {
        Node<T> gr_grandParent, grandParent, parent;
        parent = getParent(child);
        grandParent = getParent(parent);
        gr_grandParent = getParent(grandParent);

        // sibling of parent
        Node<T> s = getSiblingOfParent(grandParent, parent);

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
                    fixInsert(grandParent);
                }
            }
            break;
        }
    }

    public void printTree(Node<T> tree) {
        System.out.println("----Print tree----");
        System.out.println("------------------");
        BTreePrinter.printNode(tree);
        System.out.println();
    }

    // Goodrich p533 6th
    private static void insertExample() {
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
    // Goodrich p539 6th
    private static void deleteExample() {
        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.insert(14);
        bst.insert(7);
        bst.insert(16);
        bst.insert(4);
        bst.insert(12);
        bst.insert(15);
        bst.insert(18);
        bst.insert(3);
        bst.insert(5);
        bst.insert(17);
        bst.printTree(bst.root);

        bst.delete(3);
        bst.printTree(bst.root);

        bst.delete(12);
        bst.printTree(bst.root);

        bst.delete(17);
        bst.printTree(bst.root);

        bst.delete(18);
        bst.printTree(bst.root);

        bst.delete(15);
        bst.printTree(bst.root);

        bst.delete(16);
        bst.printTree(bst.root);
    }

    // from L17-Red-Black-Trees-clean.pptx examples
    private static void rbtree_ops() {
        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.insert(47);
        bst.printTree(bst.root);
        bst.insert(32);
        bst.printTree(bst.root);
        bst.insert(71);
        bst.printTree(bst.root);
        bst.insert(93);
        bst.printTree(bst.root);
        bst.insert(65);
        bst.printTree(bst.root);
        bst.insert(82);
        bst.printTree(bst.root);
        bst.insert(87);
        bst.printTree(bst.root);

        bst.delete(32);
        bst.printTree(bst.root);
    }
    private static void rbtree_ops2() {
        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.insert(47);
        bst.insert(32);
        bst.insert(71);
        bst.insert(65);
        bst.insert(87);
        bst.insert(25);
        bst.insert(40);
        bst.insert(50);
        bst.insert(82);
        bst.insert(93);
        bst.printTree(bst.root);

        // create test case for case 2.2.2
        Node<Integer> t = bst.find(25);
        t.setColor(BLACK);
        t = bst.find(40);
        t.setColor(BLACK);
        t = bst.find(71);
        t.setColor(BLACK);
        bst.printTree(bst.root);

        // case 2.2.2
        bst.delete(25);
        bst.printTree(bst.root);
    }

    private static void rbtree_ops3() {
        RedBlackTree<Integer> bst = new RedBlackTree<>();
        bst.insert(47);
        bst.insert(32);
        bst.insert(71);
        bst.insert(65);
        bst.insert(87);
        bst.insert(25);
        bst.insert(40);
        bst.insert(50);
        bst.insert(82);
        bst.insert(93);
        bst.insert(68);
        bst.printTree(bst.root);

        // create test case for case 2.2.2
        Node<Integer> t = bst.find(25);
        t.setColor(BLACK);
        t = bst.find(40);
        t.setColor(BLACK);

        t = bst.find(71);
        t.setColor(RED);
        t = bst.find(50);
        t.setColor(BLACK);
        t = bst.find(68);
        t.setColor(BLACK);
        t = bst.find(82);
        t.setColor(BLACK);
        t = bst.find(93);
        t.setColor(BLACK);

        bst.printTree(bst.root);

        // case 2.2.2
        bst.delete(25);
        bst.printTree(bst.root);
    }

    public static void main(String[] args) {
        // insertExample();
        // deleteExample();
        // rbtree_ops();
        // rbtree_ops2();
        rbtree_ops3();
    }
}
