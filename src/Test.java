public class Test {
    public static void main(String[] args) throws InterruptedException {
        int n = 1000;
        long[] times = new long[n];

        for (int i = 0; i < n; i++) {
            long startTime = System.nanoTime();

            Sudoku s = new Sudoku(3);
            s.generate();
            //System.out.println(s.toString());

            times[i] = System.nanoTime() - startTime;
        }

        long mean = 0;
        for (int i = 0; i < n; i++) {
            mean += times[i];
        }
        mean /= n;
        System.out.println("Mean: " + mean + "ns");
        System.out.println("Mean: " + mean/1000 + "us");

        /*
         * Mean aprox. 85000ns (85us), with 100000 iterations (original algorithm, just simple backtracking)
         * Mean aprox. 55000ns (55us), with 100000 iterations (pruning trivial fail cases,
         *      when choosing number, only those that are possible with the current matrix are available)
         * 
         */

    }

}