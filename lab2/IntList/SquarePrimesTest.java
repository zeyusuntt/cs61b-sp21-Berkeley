package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }



    @Test
    public void testSquarePrimesOne() {
        IntList lst = IntList.of(17, 15, 17, 18, 15);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("289 -> 15 -> 289 -> 18 -> 15", lst.toString());
        assertTrue(changed);
    }
}


