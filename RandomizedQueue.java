import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue
    Item[] randomQueue;
    private int head;
    private int tail;
    private int count;

    public RandomizedQueue() {
        this.randomQueue = (Item[]) new Object[1];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }

        if (this.tail == this.randomQueue.length - 1) {
            this.randomQueue = resize(this.randomQueue.length * 2);
        }

        this.randomQueue[this.tail] = item;
        this.tail++;
        this.count++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size() == this.randomQueue.length / 4) {
            this.randomQueue = resize(this.randomQueue.length / 2);
        }

        int randomItem = randomInteger();
        Item item = this.randomQueue[randomItem];
        this.randomQueue[randomItem] = null;
        this.count--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.randomQueue[this.randomInteger()];
    }

    public int randomInteger() {
        int randomItem;
        while (true) {
            int random = StdRandom.uniform(this.head, this.tail);
            if (this.randomQueue[random] != null) {
                randomItem = random;
                break;
            }
        }
        return randomItem;
    }

    public Item[] resize(int length) {
        Item[] oldArray = this.randomQueue;
        Item[] newArray = (Item[]) new Object[length];
        int i = 0;
        int j = 0;

        while (i < oldArray.length) {
            if (oldArray[i] == null) {
                i++;
            }
            else {
                newArray[j] = oldArray[i];
                i++;
                j++;
            }
        }

        this.tail = j;
        this.head = 0;
        return newArray;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i;

        public boolean hasNext() {
            return i < randomQueue.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            else {
                Item item = randomQueue[i++];
                return item;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) {
                StdOut.print(rq.dequeue());
            }
            else {
                rq.enqueue(s);
            }
        }
    }
}
