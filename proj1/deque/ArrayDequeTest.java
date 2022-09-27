package deque;
import jh61b.junit.In;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayDequeTest {
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    /*Test get function and getRecursive function.
     * first, add elements*/
    public void getIndexTest() {
        ArrayDeque<Integer> test = new ArrayDeque<Integer>();
        for (int i = 0; i<10; i++) {
            test.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            int a = test.get(i);
            assertEquals( i, a);
        }
        assertEquals(null, test.get(11));
    }

    @Test
    /*First fill up, then empty, then fill up again*/
    public void fillEmptyTest() {
        ArrayDeque<Integer> test = new ArrayDeque<Integer>();
        for (int i = 0; i < 8; i ++) {
            test.addLast(i);
        }
        for (int i = 0; i < 8; i ++) {
            test.removeFirst();
        }
        for (int i = 0; i < 8; i ++) {
            test.addFirst(i);
        }
        test.printDeque();
    }

    @Test
    /*Test equal function*/
    public void equalTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<Integer>();
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad2.addFirst(1);
        ad2.addFirst(2);
        assertTrue(ad1.equals(ad2));
        ad2.addFirst(9);
        assertFalse(ad1.equals(ad2));
        assertFalse(ad1.equals(2));
    }

    @Test
    /*Test normal add function.*/
    public void normalAddTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        ad1.addFirst(1);
        ad1.addFirst(2);
        assertEquals(2,ad1.size());

    }

    @Test
    /*Test resizing add function.*/
    public void resizingAddTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000; i ++) {
            ad1.addFirst(i);
        }
        assertEquals(1000, ad1.size());
    }

    @Test
    /*Test big amount add test.*/
    public void bigAmountAddTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100000; i ++) {
            ad1.addFirst(i);
        }
        assertEquals(100000, ad1.size());
    }

    @Test
    /*Test normal remove function.*/
    public void normalRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 7; i ++) {
            ad1.addFirst(i);
        }
        int a = ad1.removeFirst();
        assertEquals(6, a);
    }

    @Test
    /*Test resizing remove function.*/
    public void resizingRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 10; i ++) {
            ad1.addFirst(i);
        }
        for (int i = 0; i < 8; i++) {
            int a = ad1.removeLast();
            assertEquals(i, a);
            assertEquals(9-i, ad1.size());
        }

    }

    @Test
    public void bigAmountRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000; i ++) {
            ad1.addFirst(i);
        }
        for (int i = 0; i < 800; i++) {
            int a = ad1.removeLast();
            assertEquals(i, a);
            assertEquals(999-i, ad1.size());
        }
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 10; i ++) {
            ad1.addFirst(i);
        }

        for (Integer item: ad1) {

        }
    }

    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        for (int i = 0; i < 100000; i++) {
            int operationNum = StdRandom.uniform(0, 5);
            if (operationNum == 0) {
                int ranVal = StdRandom.uniform(0,100);
                a.addFirst(ranVal);
                int compare = a.get(0);
                assertEquals(ranVal, compare);
            }
            else if (operationNum == 1) {
                int ranVal = StdRandom.uniform(0,100);
                a.addLast(ranVal);
                int compare = a.get(a.size() - 1);
                assertEquals(ranVal, compare);
            }
            else if (operationNum == 2) {
                a.removeFirst();
            }
            else if (operationNum == 3) {
                a.removeLast();
            }
            else if (operationNum == 4) {
                assertTrue(a.size() >= 0);
            }
        }
    }
}
