package unsafedodo.fabricautomessage.util;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.Arrays;
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

    public T getNextData(String currentMessage){
        CircularListNode<T> current = first;
        while(current != null && !current.getData().equals(currentMessage))
            current = current.getNext();

        if(current != null && current.getNext() != null)
            return current.getNext().getData();

        return null;
    }
    public CircularLinkedList(@NotNull Collection<? extends T> c) {
        super(c);
        CircularListNode prev = null;
        CircularListNode node = null;
        for (T elem: c) {
            if (node == null) {
                node = new CircularListNode(elem, null, null);
                this.first = node;
            } else {
                node = new CircularListNode(elem, null, prev);
                prev.setNext(node);
            }
            prev = node;
            ++this.size;
        }
        this.last = node;
        this.current = this.first;
    }

    public CircularListNode<T> getNextOrFirst(){
        return current.getNext() == null ? first : current.getNext();
    }

    public CircularListNode<T> getPreviousOrLast(){
        return current.getPrevious() == null ? last : current.getPrevious();
    }

    public boolean add(T message){
        CircularListNode<T> newNode = new CircularListNode<T>(message, first, last);
        if (last != null) {
            last.setNext(newNode);
        } else {
            first = newNode;
        }
        last = newNode;
        ++size;

        first.setPrevious(newNode);

        super.add(message);
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        CircularListNode<T> node = this.first;
        for (int i = 0; i < this.size; i++) {
            array[i] = node.getData();
            node = node.getNext();
        }
        return array;
    }

    public String[] objToStringArray(Object[] objArray){
        return Arrays.stream(objArray).map(Object::toString).toArray(String[]::new);
    }

    public T remove(int index){
        //check if works
        if(index >= size)
            throw new InvalidParameterException(String.format("List does not contain %d elements", index));

        CircularListNode<T> nodeToRemove = first;

        for(int i = 0; i < index; i++){
            nodeToRemove = nodeToRemove.getNext();
        }

        T removed = nodeToRemove.getData();
        if(size == 1){
            first = null;
            last = null;
        } else{
            if(nodeToRemove == first)
                first = nodeToRemove.getNext();
            else if(nodeToRemove == last)
                last = nodeToRemove.getPrevious();
            else{
                nodeToRemove.getPrevious().setNext(nodeToRemove.getNext());
                nodeToRemove.getNext().setPrevious(nodeToRemove.getPrevious());
            }
        }
        /*if(index == 0){
            last.setNext(firstCopy.getNext());
            firstCopy.getNext().setPrevious(last);
        } else{
            firstCopy.getPrevious().setNext(firstCopy.getNext());
            firstCopy.getNext().setPrevious(firstCopy.getPrevious());
        }*/
        size--;
        super.remove(index);
        return removed;
    }
}
