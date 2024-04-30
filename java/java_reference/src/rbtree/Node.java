package rbtree;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    Node<T> left, right;
    T key;

    public T getKey() {
        return key;
    }

    public Node<T> getLeft(){
        return left;
    }

    public Node<T> getRight(){
        return right;
    }

    public Node(T data) {
        this.key = data;
    }

    public Node(T data, Node l, Node r) {
        this.key = data;
        this.left = l;
        this.right = r;
    }

    @Override
    public int compareTo(Node<T> o) {
        return getKey().compareTo(o.getKey());
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