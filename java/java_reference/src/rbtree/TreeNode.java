package rbtree;

public class TreeNode<T extends Comparable<T>> extends Node<T> {
    public TreeNode(T data) {
        super(data);
    }

    public TreeNode(T data, Node l, Node r) {
        super(data, l, r);
    }
}
