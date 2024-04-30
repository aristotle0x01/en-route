package rbtree;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node.TreeNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public BinarySearchTree(Node.TreeNode<T> node) {
        this.root = node;
    }

    public BinarySearchTree(T v) {
        this.root = new Node.TreeNode<>(v, null, null);
    }

    // false only if the key already exists
    public void insert(T key) {
        if (root == null) {
            root = new Node.TreeNode<>(key, null, null);
            return;
        }

        Node.TreeNode<T> parent = root;
        while (true) {
            int cmp = parent.key.compareTo(key);
            if (cmp == 0) {
                return;
            } else if (cmp > 0) {
                // to left
                if (parent.left == null) {
                    parent.left = new Node.TreeNode<>(key, null, null);
                    return;
                } else {
                    parent = (Node.TreeNode<T>) parent.left;
                }
            } else {
                // to right
                if (parent.right == null) {
                    parent.right = new Node.TreeNode<>(key, null, null);
                    return;
                } else {
                    parent = (Node.TreeNode<T>) parent.right;
                }
            }
        }
    }

    // return null if key not exists
    public Node.TreeNode<T> find(T key) {
        Node.TreeNode<T> p = root;
        while (p != null) {
            int cmp = p.key.compareTo(key);
            if (cmp == 0) {
                return p;
            } else if (cmp > 0) {
                p = (Node.TreeNode<T>) p.left;
            } else {
                p = (Node.TreeNode<T>) p.right;
            }
        }

        return null;
    }

    // false only key not exists
    public void delete(T key) {
        Node.TreeNode<T> parent, node;
        parent = null;
        node = root;
        while (node != null) {
            int cmp = node.key.compareTo(key);
            if (cmp == 0) {
                break;
            }
            parent = node;
            if (cmp > 0) {
                node = (Node.TreeNode<T>) node.left;
            } else {
                node = (Node.TreeNode<T>) node.right;
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
        Node.TreeNode<T> swapNode = null, parentOfSwapNode = node;
        if (node.left != null) {
            // rightmost node of left subtree
            swapNode = (Node.TreeNode<T>) node.left;
            while (swapNode.right != null) {
                parentOfSwapNode = swapNode;
                swapNode = (Node.TreeNode<T>) swapNode.right;
            }
            if (parentOfSwapNode != node) {
                parentOfSwapNode.right = swapNode.left;
            } else {
                node.left = null;
            }
        } else {
            // leftmost node of right subtree
            swapNode = (Node.TreeNode<T>) node.right;
            while (swapNode.left != null) {
                parentOfSwapNode = swapNode;
                swapNode = (Node.TreeNode<T>) swapNode.left;
            }
            if (parentOfSwapNode != node) {
                parentOfSwapNode.left = swapNode.right;
            } else {
                node.right = null;
            }
        }
        node.key = swapNode.key;

    }

    public void printTree(Node.TreeNode<T> tree) {
        System.out.println("----Print tree----");
        System.out.println("------------------");
        BTreePrinter.printNode(tree);
        System.out.println();
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(10);
        bst.insert(7);
        bst.insert(15);
        bst.insert(18);
        bst.insert(5);
        bst.insert(13);
        bst.insert(8);
        bst.insert(21);
        bst.insert(14);
        bst.printTree(bst.root);

        Node.TreeNode<Integer> node = bst.find(8);

        bst.delete(15);
        bst.printTree(bst.root);
        bst.delete(10);
        bst.printTree(bst.root);
        bst.delete(13);
        bst.printTree(bst.root);
        bst.delete(18);
        bst.printTree(bst.root);
    }
}
