package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op"); //Basic information
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static double helpermethod(int i){
        SLList CreateNewList = new SLList<Integer>();
        for(int j = 0; j < i; j++)
        {
            CreateNewList.addLast(1);
        }

        Stopwatch sw = new Stopwatch();
        for(int z = 0; z < 10000; z++)
        {
            CreateNewList.getLast();
        }
        return sw.elapsedTime();
    }
    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast()
    {
        AList n_storage = new AList<Integer>();
        AList seconds_storage = new AList<Double>();
        AList operations_storage = new AList<Integer>();

        for(int i = 1000, j = 0; j < 8; j++, i*=2)
        {
            n_storage.addLast(i);
            seconds_storage.addLast(helpermethod(i));
            operations_storage.addLast(10000);
        }

        printTimingTable(n_storage, seconds_storage, operations_storage);
    }
}