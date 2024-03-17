/*
 * TO-DO:
 * > Game loop
 * > Selection of difficulty (removing numbers randomly while still having only one solution)
 * > Sudoku solving
 */


public class Main {
    public static void main(String[] args) {
        int n = 100000;
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

        /*
         * Mean aprox. 85000ns, with 100000 iterations (original algorithm, just simple backtracking)
         * Mean aprox. 55000ns, with 100000 iterations (pruning trivial fail cases,
         *      when choosing number, only those that are possible with the current matrix are available)
         * 
         */ 


    }

}