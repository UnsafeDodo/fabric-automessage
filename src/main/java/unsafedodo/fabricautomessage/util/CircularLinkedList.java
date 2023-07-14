package unsafedodo.fabricautomessage.util;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedList;

public class CircularLinkedList<T> extends LinkedList<T> {
    private CircularListNode<T> current;
    private CircularListNode<T> first;
    private CircularListNode<T> last;
    private int size;

    public int getSize() {
        return size;
    }

    public CircularLinkedList() {
        this.first = null;
        this.last = null;
        this.current = null;

    }
    public CircularLinkedList(@NotNull Collection<? extends T> c) {
        super(c);
    }

    public CircularListNode<T> getNextOrFirst(){
        return current.getNext() == null ? first : current.getNext();
    }

    public CircularListNode<T> getPreviousOrLast(){
        return current.getPrevious() == null ? last : current.getPrevious();
    }

    public boolean add(T message){
        CircularListNode<T> newNode = new CircularListNode<T>(message, null, last);
        last.setNext(newNode);
        last = newNode;
        size++;
        return true;
    }

    public T remove(int index){
        if(index >= size)
            throw new InvalidParameterException(String.format("List does not contain %d elements", index));

        CircularListNode<T> firstCopy = first;

        for(int i = 0; i < index; i++){
            firstCopy = firstCopy.getNext();
        }
        T removed = firstCopy.getData();
        firstCopy.getPrevious().setNext(firstCopy.getNext());
        firstCopy.getNext().setPrevious(firstCopy.getPrevious());
        size--;
        return removed;
    }
}
