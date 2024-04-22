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
}