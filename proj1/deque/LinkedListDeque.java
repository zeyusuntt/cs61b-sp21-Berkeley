package deque;

import java.util.Deque;
import java.util.Iterator;

public class LinkedListDeque<Item> {
    private class IntNode {
        public Item item;
        public IntNode prev;
        public IntNode next;


        public IntNode(Item i, IntNode m, IntNode n) {
            item = i;
            prev = m;
            next = n;
        }
    }
    /* The first item (if it exits) is at sentinel.next.
     * */
    private IntNode sentinel;
    private int size;

    /*Creates an empty LinkedListDeque.
    * What's the difference: The last node points to sentinel, not to null.*/
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel.prev;
        size = 0;
    }

    public LinkedListDeque(Item x) {
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /*Add an item to the front of the deque. We could assume that item is never null.*/
    public void addFirst(Item item) {
        IntNode second = sentinel.next;
        IntNode first = new IntNode(item, sentinel, second);
        sentinel.next = first;
        second.prev = first;
        size += 1;
    }

    /*Adds an item to the back of the deque. We could assume that item is never null.*/
    public void addLast(Item item) {
        IntNode secondLast = sentinel.prev;
        IntNode last = new IntNode(item, secondLast, sentinel);
        secondLast.next = last;
        sentinel.prev = last;
        size += 1;
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /*Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /*Print the items in the deque from first to last, separated by a space. Once all the items have been printed,
    * print out a new line.*/
    public void printDeque() {
        IntNode cur = sentinel.next;
        while (cur.item != null) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /*Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public Item removeFirst() {
        IntNode p = sentinel;
        if (p.next.item != null) {
            IntNode first = p.next;
            p.next = p.next.next;
            p.next.prev = p;
            first.next = null;
            first.prev = null;
            size -= 1;
            return first.item;
        }
        return null;
    }

    /*Removes and returns the item at the last of the deque. If no such item exists, returns null.*/
    public Item removeLast() {
        IntNode p = sentinel;
        if (p.prev.item != null) {
            IntNode last = p.prev;
            p.prev = p.prev.prev;
            p.prev.next = p;
            last.prev = null;
            last.next = null;
            size -= 1;
            return last.item;
        }
        return null;
    }

    /*Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists,
    * return null. Must not alter the deque.*/
    public Item get(int index) {
        IntNode p = sentinel.next;
        if (index < size) {
            int i = index;
            while (i != 0) {
                p = p.next;
                i--;
            }
            return p.item;
        }
        return null;
    }

    public Item getRecursive(int index) {
        IntNode p = sentinel;
        return getRecursiveHelper(p.next, index);
    }

    public Item getRecursiveHelper(IntNode cur, int index) {
        while (cur.item != null && index >0) {
            return getRecursiveHelper(cur.next, index-1);
        }
        return cur.item;
    }

//    /*The Deque objects we'll make are iterable, so we must provide this method to return an iterator.*/
//    public Iterator<Item> iterator() {
//
//    }
//
//    /*Returns whether the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it
//    * contains the same contents in the same order*/
//    public boolean equals(Object o) {
//
//        return true;
//    }


}


