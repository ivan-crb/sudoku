import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BoxGeneration extends Thread {
    private int[][] matrix;
    private int innerSize;
    private int size;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public BoxGeneration(int[][] matrix, int innerSize, int size, int startX, int startY) {
        this.matrix = matrix;
        this.innerSize = innerSize;
        this.size = size;
        this.startX = startX;
        this.startY = startY;
        this.endX = startX + innerSize;
        this.endY = startY + innerSize;
    }

    public void run() {
        Set<Integer> availableNums = new HashSet<Integer>();
        for (int i = 1; i <= size; i++) availableNums.add(i);
        
        boolean isValid = false;
        while (!isValid && availableNums.size() > 0) {
            int currentNumber = getRandomNumber(availableNums);

            isValid = generate(startX, startY, currentNumber);
            if (!isValid) matrix[startX][startY] = 0;
            
            availableNums.remove(currentNumber);
        }
    }

    private boolean generate(int row, int col, int number) {
        matrix[row][col] = number;

        if (!horizontalCheck(row, col, number)) return false;
        if (!verticalCheck(row, col, number)) return false;
        if (!boxCheck(row, col, number)) return false;
        
        col++;
        if (col == endY) row++;
        col = col % size;
        if (row == endX) return true;

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
}

