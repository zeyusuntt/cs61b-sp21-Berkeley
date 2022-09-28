package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void testStudentAndSolution() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ad1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addLast(i);
                ad1.addLast(i);
                assertEquals(sad1.size(), ad1.size());
                assertEquals("addLast(" + i + ")", sad1.get(sad1.size()-1), ad1.get(ad1.size()-1));
            } else if (numberBetweenZeroAndOne > 0.25 && numberBetweenZeroAndOne < 0.50) {
                sad1.addFirst(i);
                ad1.addFirst(i);
                assertEquals(sad1.size(), ad1.size());
                assertEquals("addFirst(" + i + ")",sad1.get(0), ad1.get(0));
            }
            else if (numberBetweenZeroAndOne > 0.50 && numberBetweenZeroAndOne < 0.75) {
                if (ad1.size() != 0 && sad1.size() != 0) {
                    Integer a = sad1.removeFirst();
                    Integer b = ad1.removeFirst();
                    assertEquals(sad1.size(), ad1.size());
                    assertEquals("removeFirst()", a, b);
                }
            }
            else {
                if (ad1.size() != 0 && sad1.size() != 0) {
                    Integer a = sad1.removeLast();
                    Integer b = ad1.removeLast();
                    assertEquals(sad1.size(), ad1.size());
                    assertEquals("removeLast", a, b);
                }
            }
        }

        sad1.printDeque();
        ad1.printDeque();
    }
}
