package rbtree;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    public final static boolean RED = true;
    public final static boolean BLACK = false;

    // package-private: all members are visible within the same package but aren’t accessible from other packages
    Node<T> left, right, parent;
    T key;
    boolean color;

    public Node(T k) {
        this.key = k;
    }

    public Node(T data, Node l, Node r) {
        this.key = data;
        this.left = l;
        this.right = r;
    }

    public Node(T data, Node l, Node r, Node p, boolean color) {
        this.key = data;
        this.left = l;
        this.right = r;
        this.parent = p;
        this.color = color;
    }

    public Node<T> getLeft(){
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight(){
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public void flipColor() {
        this.color = !this.color;
    }

    @Override
    public int compareTo(Node<T> o) {
        return getKey().compareTo(o.getKey());
    }

    public static class ExternalNode<T extends Comparable<T>> extends Node<T> {
        public ExternalNode(T data) {
            super(null);
            this.setColor(BLACK);
        }

        public ExternalNode(T key, Node<T> p) {
            super(null);
            this.setColor(BLACK);
            this.parent = p;
        }
    }

    public static class ColorTreeNode<T extends Comparable<T>> extends Node<T> {
        private boolean color;

        public final static boolean RED = true;
        public final static boolean BLACK = false;

        public ColorTreeNode(T data) {
            super(data);
            this.color = BLACK;
        }

        public ColorTreeNode(T data, Node<T> l, Node<T> r, boolean color) {
            super(data, l, r);
            this.color = color;
        }

        public boolean getColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public void flipColor() {
            this.color = !this.color;
        }
    }

    public static class TreeNode<T extends Comparable<T>> extends Node<T> {
        public TreeNode(T data) {
            super(data);
        }

        public TreeNode(T data, Node l, Node r) {
            super(data, l, r);
        }
    }
}