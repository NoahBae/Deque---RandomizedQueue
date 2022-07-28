import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    private Node head;
    private Node tail;
    private int count = 0;

    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("NullPointerException");
        }
        Node newNode = new Node(item);
        newNode.next = this.head;
        if (isEmpty()) {
            this.head = newNode;
            this.tail = this.head;
            this.count++;
            return;
        }
        this.head.prev = newNode;
        this.head = newNode;
        this.count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("NullPointerException");
        }

        if (isEmpty()) {
            this.tail = new Node(item);
            this.head = this.tail;
            this.count++;
            return;
        }

        this.tail.next = new Node(item);
        this.tail.next.prev = this.tail;
        this.tail = this.tail.next;
        this.count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty dequeue");
        }
        Node former = this.head;
        this.head = this.head.next;
        this.count--;
        return former.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty dequeue");
        }

        if (this.tail == this.head && this.count == 1) {
            Item item = this.head.item;
            this.head = null;
            return item;
        }

        Item item = this.tail.item;
        this.tail = this.tail.prev;
        this.tail.next = null;
        this.count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = head;

        public boolean hasNext() {
            return this.current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }


        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.print(deque.removeLast());
            }
            else {
                deque.addFirst(s);
            }
        }
    }
}
