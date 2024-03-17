import java.util.Collection;
import java.util.Set;

import java.util.HashSet;

public class Sudoku {
    private int[][] matrix;
    private int innerSize;
    private int size;

    public Sudoku(int innerSize) {
        this.innerSize = innerSize; // Has to be between 2 and 4
        this.size = innerSize*innerSize;
        this.matrix = new int[size][size];

        // Initializes matrix to 0 (just in case)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void generate() throws InterruptedException {
        generate(false);
    }

    public void generate(boolean concurrent) throws InterruptedException {
        
        // Concurrent for learning purposes, slower than the other version (probably due to threads inicializations)
        if (concurrent) { 
            for (int i = 0; i < innerSize; i++) {
                BoxGeneration[] boxGens = new BoxGeneration[innerSize];
    
                for (int j = 0; j < innerSize; j++) {
                    int x = (innerSize * j + innerSize * i) % size;
                    int y = innerSize * j;
    
                    BoxGeneration gen = new BoxGeneration(matrix, innerSize, size, x, y);
                    boxGens[j] = gen;
                    gen.start();
                }
    
                for (int j = 0; j < innerSize; j++) {
                    boxGens[j].join();
                }    
            }
        }
        else {
            Set<Integer> availableNums = new HashSet<Integer>();
            for (int i = 1; i <= size; i++) availableNums.add(i);
            
            boolean isValid = false;
            while (!isValid && availableNums.size() > 0) {
                int currentNumber = getRandomNumber(availableNums);
    
                isValid = generate(0, 0, currentNumber);
                if (!isValid) matrix[0][0] = 0;
                
                availableNums.remove(currentNumber);
            }
        }
    }

    private boolean generate(int row, int col, int number) {
        matrix[row][col] = number;

        if (!horizontalCheck(row, col, number)) return false;
        if (!verticalCheck(row, col, number)) return false;
        if (!boxCheck(row, col, number)) return false;
        
        col++;
        if (col == size) row++;
        col = col % size;
        if (row == size) return true;

        Set<Integer> availableNums = new HashSet<Integer>();
        for (int i = 1; i <= size; i++) {
            boolean validNum = true;

            if (!horizontalCheck(row, col, i)) validNum = false;
            if (!verticalCheck(row, col, i)) validNum = false;
            if (!boxCheck(row, col, i)) validNum = false;

            if (validNum) availableNums.add(i);
        }

        boolean isValid = false;
        while (!isValid && availableNums.size() > 0) {
            int currentNumber = getRandomNumber(availableNums);

            isValid = generate(row, col, currentNumber);
            if (!isValid) matrix[row][col] = 0;
            
            availableNums.remove(currentNumber);
        }
        return isValid;
    }

    private boolean horizontalCheck(int row, int col, int number) {
        boolean valid = true;
        for (int i = 0; i < size && valid; i++) if (matrix[row][i] == number && i != col) valid = false;
        return valid;
    }

    private boolean verticalCheck(int row, int col, int number) {
        boolean valid = true;
        for (int i = 0; i < size && valid; i++) if (matrix[i][col] == number && i != row) valid = false;
        return valid;
    }

    private boolean boxCheck(int row, int col, int number) {
        boolean valid = true;
        int startRow = innerSize * (row / innerSize);
        int startCol = innerSize * (col / innerSize);
        for (int i = startRow; i < startRow + innerSize; i++) {
            for (int j = startCol; j < startCol + innerSize; j++) {
                if (matrix[i][j] == number && i != row && j != col) valid = false;
            }
        }
        return valid;
    }

    private int getRandomNumber(Collection<Integer> col) {
        int[] numbers = new int[col.size()];
        int i = 0;
        for (Integer integer : col) {
            numbers[i] = integer;
            i++;
        }
        return numbers[(int) Math.floor(Math.random() * numbers.length)];
    }

    public String toString() {
        String res = "";

        for (int i = 0; i < size; i++) {
            if (i % innerSize == 0 && i != 0) {
                for (int j = 0; j < 3*(size+innerSize-1); j++) res += "-";
                res += "\n";
            }

            for (int j = 0; j < size; j++) {
                if (j % innerSize == 0 && j != 0) res += " | ";
                res += " " + toHex(matrix[i][j]) + " ";
            }
            res += "\n";
        }

        return res;
    }

    private String toHex(int num) {
        switch (num) {
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
            case 16:
                return "G";       
            default:
                return "" + num;
        }
    }
}