package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

//    @Test
//    public void testStudentAndSolution() {
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ad1 = new ArrayDequeSolution<>();
//
//        for (int i = 0; i < 10000; i += 1) {
//            double numberBetweenZeroAndOne = StdRandom.uniform();
//
//            if (numberBetweenZeroAndOne < 0.25) {
//                sad1.addLast(i);
//                ad1.addLast(i);
//                assertEquals(sad1.size(), ad1.size());
//                assertEquals("addLast(" + i + ")", sad1.get(sad1.size()-1), ad1.get(ad1.size()-1));
//            } else if (numberBetweenZeroAndOne > 0.25 && numberBetweenZeroAndOne < 0.50) {
//                sad1.addFirst(i);
//                ad1.addFirst(i);
//                assertEquals(sad1.size(), ad1.size());
//                assertEquals("addFirst(" + i + ")",sad1.get(0), ad1.get(0));
//            }
//            else if (numberBetweenZeroAndOne > 0.50 && numberBetweenZeroAndOne < 0.75) {
//                if (ad1.size() != 0 && sad1.size() != 0) {
//                    Integer a = sad1.removeFirst();
//                    Integer b = ad1.removeFirst();
//                    assertEquals(sad1.size(), ad1.size());
//                    assertEquals("removeFirst()", a, b);
//                }
//            }
//            else {
//                if (ad1.size() != 0 && sad1.size() != 0) {
//                    Integer a = sad1.removeLast();
//                    Integer b = ad1.removeLast();
//                    assertEquals(sad1.size(), ad1.size());
//                    assertEquals("removeLast", a, b);
//                }
//            }
//        }
//
//        sad1.printDeque();
//        ad1.printDeque();
//    }


    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> testArray = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> stdArray = new ArrayDequeSolution<>();
        String log = "";
        for (int i = 0; i < 1000; i++) {
            if (stdArray.size() == 0) {
                int addNumber = StdRandom.uniform(1000);
                int headOrBack = StdRandom.uniform(2);
                if (headOrBack == 0) {
                    log = log + "addFirst(" + addNumber + ")\n";
                    testArray.addFirst(addNumber);
                    stdArray.addFirst(addNumber);
                } else {
                    log = log + "addLast(" + addNumber + ")\n";
                    testArray.addLast(addNumber);
                    stdArray.addLast(addNumber);
                }
            } else {
                int x = StdRandom.uniform(4);
                int addNumber = StdRandom.uniform(1000);
                Integer testremoveNumber = 1;
                Integer stdremoveNumber = 1;
                switch (x) {
                    case 0:
                        log = log + "addFirst(" + addNumber + ")\n";
                        testArray.addFirst(addNumber);
                        stdArray.addFirst(addNumber);
                        break;
                    case 1:
                        log = log + "addLast(" + addNumber + ")\n";
                        testArray.addLast(addNumber);
                        stdArray.addLast(addNumber);
                        break;
                    case 2:
                        log = log + "removeFirst()\n";
                        testremoveNumber = testArray.removeFirst();
                        stdremoveNumber = stdArray.removeFirst();
                        break;
                    case 3:
                        log = log + "removeLast()\n";
                        testremoveNumber = testArray.removeLast();
                        stdremoveNumber = stdArray.removeLast();
                        break;
                    default:
                }
                assertEquals(log, stdremoveNumber, testremoveNumber);
            }
        }

    }
}
