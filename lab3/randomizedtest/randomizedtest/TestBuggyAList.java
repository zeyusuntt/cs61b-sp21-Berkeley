package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {
      randomizedtest.AListNoResizing<Integer> correct = new randomizedtest.AListNoResizing<>();
      randomizedtest.BuggyAList<Integer> broken = new randomizedtest.BuggyAList<>();

      correct.addLast(5);
      correct.addLast(10);
      correct.addLast(15);

      broken.addLast(5);
      broken.addLast(10);
      broken.addLast(15);

      assertEquals(correct.size(), broken.size());

      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
  }


    @Test
    public void randomizedTest() {
      randomizedtest.AListNoResizing<Integer> L = new randomizedtest.AListNoResizing<>();
      randomizedtest.BuggyAList<Integer> B = new randomizedtest.BuggyAList<>();
      int N = 5000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 3);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              B.addLast(randVal);
//              System.out.println("addLast(" + randVal + ")");
          } else if (operationNumber == 1) {
              // size
              if (L.size() != 0 && B.size() != 0) {
                  int t = L.getLast();
                  int s = B.getLast();
//                  System.out.println("removeLast for L: " + t);
//                  System.out.println("removeLast for B: " + s);
                  assertEquals(L.removeLast(),B.removeLast());
              }
          } else if (operationNumber == 2) {
              if (L.size() != 0 && B.size() != 0) {
//                  System.out.println("getLast for L: " + L.getLast());
//                  System.out.println("getLast for B: " + B.getLast());
                  assertEquals(L.getLast(),B.getLast());
              }
          }
      }
  }
}
