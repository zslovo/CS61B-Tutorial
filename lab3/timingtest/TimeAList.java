package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
    public static void Helpermethod(int i)
    {
        AList CreateNewList  = new AList<Integer>();
        for (int j = 0; j < i; j++)
        {
            CreateNewList.addLast(0);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
            //add data to N_storage
            AList N_storage = new AList<Integer>();
            AList Seconds_Storage = new AList<Double>();

            for(int i = 1000, j = 0; j < 8; i*=2, j++)
            {
                Stopwatch sw = new Stopwatch();
                Helpermethod(i);
                Seconds_Storage.addLast(sw.elapsedTime());
                N_storage.addLast(i);
            }
            printTimingTable(N_storage,Seconds_Storage,N_storage);
    }
}
