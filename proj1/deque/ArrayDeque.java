package deque;

import java.util.Iterator;

public class ArrayDeque<Item> implements  Deque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;

    /*Create an empty list.*/
    public ArrayDeque() {
        capacity = 8;
        items = (Item[]) new Object[capacity];
        size = 0;
        nextFirst = 7;
        nextLast = 0;

    }

    /*Resize the underlying array to the target capacity.*/
    private void resize(int x) {
        Item[] a = (Item[]) new Object[x];
        for (int i = 0; i < size; i++) {
            a[i] = get(i);
        }
        items = a;
        capacity = x;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /*Add an item to the front of the deque. We could assume that item is never null.*/
    public void addFirst(Item x) {
        if (size == capacity) {
            resize(2*capacity);
        }
        items[nextFirst] = x;
        int copy = nextFirst;
        if (nextFirst == 0) {
            nextFirst = capacity - 1;
        }
        else {
            nextFirst = copy - 1;
        }
        size += 1;
    }

    /*Adds an item to the back of the deque. We could assume that item is never null.*/
    public void addLast(Item x) {
        if (size == capacity) {
            resize(2*capacity);
        }
        items[nextLast] = x;
        int copy = nextLast;
        if (nextLast == capacity - 1) {
            nextLast = 0;
        }
        else {
            nextLast = copy + 1;
        }
        size += 1;
    }

    /*Returns true if deque is empty, false otherwise.*/
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /*Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /*Print the items in the deque from first to last, separated by a space. Once all the items have been printed,
     * print out a new line.*/
    public void printDeque() {
        if (size == 0) {
            System.out.println("Error: you should print an unempty Deque");
        }
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i <nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
        else if (nextFirst > nextLast) {
            for (int i = nextFirst + 1; i <capacity; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i =0; i <nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    /*Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        Item result;
        if (nextFirst == capacity - 1) {
            result = items[0];
            nextFirst = 0;
        }
        else {
            result = items[nextFirst + 1];
            nextFirst = nextFirst + 1;
        }
        size--;
        if (size == 0) {
            resize(4);
        }
        else if ((double)size/capacity < 0.25) {
            resize(capacity/4);
        }
        return result;
    }

    /*Removes and returns the item at the last of the deque. If no such item exists, returns null.*/
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        Item result;
        if (nextLast == 0) {
            result = items[capacity - 1];
            nextLast = capacity - 1;
        }
        else {
            result = items[nextLast - 1];
            nextLast = nextLast - 1;
        }
        size--;
        if (size == 0) {
            resize(4);
        }
        double a = (float) size/capacity;
        if (a < 0.25) {
            resize(capacity/4);
        }
        return result;
    }

    /*Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists,
     * return null. Must not alter the deque.*/
    public Item get(int index) {
        if (index > size) {
            return null;
        }
        if (nextLast > nextFirst && nextLast == 0) {
            return (items[nextFirst + index + 1]);
        }
        else {
            if (index + 1 + nextFirst < capacity) {
                return (items[nextFirst + index + 1]);
            }
            else {
                return (items[nextFirst + index + 1 - capacity]);
            }
        }
    }



    /*The Deque objects we'll make are iterable, so we must provide this method to return an iterator.*/
    public Iterator<Item> iterator() {
        return new ArrayDequeIterator();
    }



    /*Returns whether the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it
    * contains the same contents in the same order*/
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque<?>)) {
            return false;
        }
        ArrayDeque<?> comparison = (ArrayDeque<?>) o;
        if (comparison.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if(comparison.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    public class ArrayDequeIterator implements Iterator<Item> {
        private int index;
        ArrayDequeIterator() {
            index = 0;
        }
        public boolean hasNext() {
            return index < size;
        }
        public Item next() {
            Item returnItem = get(index);
            index += 1;
            return returnItem;
        }
    }
}
