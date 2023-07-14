package unsafedodo.fabricautomessage.util;

public class CircularListNode<T> {
    private T data;
    private CircularListNode<T> next;
    private CircularListNode<T> previous;

    public CircularListNode(T data, CircularListNode<T> next, CircularListNode<T> previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    public CircularListNode(){
        this(null, null, null);
    }

    public void setNext(CircularListNode<T> next) {
        this.next = next;
    }

    public void setPrevious(CircularListNode<T> previous) {
        this.previous = previous;
    }

    public CircularListNode<T> getNext() {
        return next;
    }

    public CircularListNode<T> getPrevious() {
        return previous;
    }

    public T getData() {
        return data;
    }
}
