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
        String log = "";
        for (int i = 0; i < 10000; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            Integer a = 1;
            Integer b = 1;
            if (numberBetweenZeroAndOne < 0.25) {
                sad1.addLast(i);
                ad1.addLast(i);
                log = log + "addLast(" + i + ")\n";
            } else if (numberBetweenZeroAndOne > 0.25 && numberBetweenZeroAndOne < 0.50) {
                sad1.addFirst(i);
                ad1.addFirst(i);
                log = log + "addFirst(" + i + ")\n";
            }
            else if (numberBetweenZeroAndOne > 0.50 && numberBetweenZeroAndOne < 0.75) {
                if (ad1.size() != 0 && sad1.size() != 0) {
                    a = sad1.removeFirst();
                    b = ad1.removeFirst();
                    log = log + "removeFirst()\n";
                }
            }
            else {
                if (ad1.size() != 0 && sad1.size() != 0) {
                    a = sad1.removeLast();
                    b = ad1.removeLast();
                    log = log + "removeLast()\n";
                }
            }
            assertEquals(log, a, b);
        }
    }


//    @Test
//    public void testStudentArrayDeque() {
//        StudentArrayDeque<Integer> testArray = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> stdArray = new ArrayDequeSolution<>();
//        String log = "";
//        for (int i = 0; i < 1000; i++) {
//            if (stdArray.size() == 0) {
//                int addNumber = StdRandom.uniform(1000);
//                int headOrBack = StdRandom.uniform(2);
//                if (headOrBack == 0) {
//                    log = log + "addFirst(" + addNumber + ")\n";
//                    testArray.addFirst(addNumber);
//                    stdArray.addFirst(addNumber);
//                } else {
//                    log = log + "addLast(" + addNumber + ")\n";
//                    testArray.addLast(addNumber);
//                    stdArray.addLast(addNumber);
//                }
//            } else {
//                int x = StdRandom.uniform(4);
//                int addNumber = StdRandom.uniform(1000);
//                Integer testremoveNumber = 1;
//                Integer stdremoveNumber = 1;
//                switch (x) {
//                    case 0:
//                        log = log + "addFirst(" + addNumber + ")\n";
//                        testArray.addFirst(addNumber);
//                        stdArray.addFirst(addNumber);
//                        break;
//                    case 1:
//                        log = log + "addLast(" + addNumber + ")\n";
//                        testArray.addLast(addNumber);
//                        stdArray.addLast(addNumber);
//                        break;
//                    case 2:
//                        log = log + "removeFirst()\n";
//                        testremoveNumber = testArray.removeFirst();
//                        stdremoveNumber = stdArray.removeFirst();
//                        break;
//                    case 3:
//                        log = log + "removeLast()\n";
//                        testremoveNumber = testArray.removeLast();
//                        stdremoveNumber = stdArray.removeLast();
//                        break;
//                    default:
//                }
//                assertEquals(log, stdremoveNumber, testremoveNumber);
//            }
//        }

//    }
}
