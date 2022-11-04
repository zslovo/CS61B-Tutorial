package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    public static void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);



    }
    public static void randomizedTest()
    {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);

            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size_correct = correct.size();
                int size_broken = broken.size();
                System.out.println("size_correct: " + size_correct);
                System.out.println("size_broken: " + size_broken);
            }
            else if (operationNumber ==2 )
            {
                //getLast
                if(correct.size() > 0)
                {
                    System.out.println("LastItem:" + correct.getLast());
                    System.out.println("RemoveItem:" + correct.removeLast());
                }
                if(broken.size() > 0)
                {
                    System.out.println("LastItem_broken:" + broken.getLast());
                    System.out.println("RemoveItem_broken: " + broken.removeLast());
                }
            }
        }
    }
    public static void main(String[] args) {
        randomizedTest();
    }
}