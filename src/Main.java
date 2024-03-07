/*
 * TO-DO:
 * > Game loop
 * > Selection of difficulty (removing numbers randomly while still having only one solution)
 * > Sudoku solving
 */

public class Main {
    public static void main(String[] args) {

        Sudoku s = new Sudoku(3);
        s.generate();


        
        
        System.out.println(s.toString());
    }

}