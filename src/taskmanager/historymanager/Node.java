package taskmanager.historymanager;

public class Node<T> {
    Node<T> next;
    Node<T> prev;
    T data;
    Node(T data, Node<T> next, Node<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
