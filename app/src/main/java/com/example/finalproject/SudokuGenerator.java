/**
 * This class generates the unique sudoku puzzles for gameplay. It starts by
 * filling in the diagonal 3x3 boxes, and then filling in the remaining cells.
 */
package com.example.finalproject;

public class SudokuGenerator {

    int[][]mat;//2d array that used to generator numbers
    int[][]full;//2d array that store full board
    int[][]puzzle;//2d array that store board with cells removed
    int N; // number of columns/rows.
    int sqrN; // square root of N
    int hides; // No. Of missing digits

    /**
     * Default constructor
     */
    SudokuGenerator() {
        //N represents row and column = 9;
        this.N = 9;
        this.hides = 20;
        // Compute square root of N will be = 3
        Double SRNd = Math.sqrt(N);
        sqrN = SRNd.intValue();
        mat = new int[N][N];
    }

    /**
     * Returns a full board which will be used as a solution.
     * @return
     */
    public int[][] fillValues() {
        // Fill the diagonal of sqrN x sqrN matrices = 3x3
        fillDiagonal();
        // Fill remaining blocks
        fillRemaining(0, sqrN);
        full = copy(mat);
        return full;
    }

    /**
     * Fills the diagonal 3x3 boxes.
     */
    void fillDiagonal() {
        for (int i = 0; i<N; i=i+ sqrN){
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
        }
    }

    /**
     * Fills a 3x3 box with random numbers.
     * @param row The row of the 3x3 box.
     * @param col The column of the 3x3 box
     */
    void fillBox(int row,int col) {
        int num;
        for (int i = 0; i< sqrN; i++)
        {
            for (int j = 0; j< sqrN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    /**
     * Checks if a number already exists in a 3x3 box.
     * @param rowStart Row that this 3x3 box starts at
     * @param colStart Column that this 3x3 box starts at
     * @param num Number to check
     * @return true if used; false if else
     */
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i< sqrN; i++)
            for (int j = 0; j< sqrN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    /**
     * Random number generator from 1-num.
     * @param num End range
     * @return Random number
     */
    int randomGenerator(int num) {
        return (int) Math.floor((Math.random()*num+1));
    }

    /**
     * Checks if it's safe to put a number in a row, column, and 3x3 box.
     * @param i Row
     * @param j Column
     * @param num Number to check
     * @return true if safe; false if else
     */
    boolean CheckIfSafe(int i,int j,int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i% sqrN, j-j% sqrN, num));
    }

    /**
     * Checks if a number already exists in a row.
     * @param i Row
     * @param num Number to check
     * @return true if does not exist; false if else
     */
    boolean unUsedInRow(int i,int num) {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Checks if a number already exists in a column.
     * @param j Column
     * @param num Number to check
     * @return true if does not exist; false if else
     */
    boolean unUsedInCol(int j,int num) {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    /**
     * Fills the remaining cells.
     * @param i Row
     * @param j Column
     * @return True if successful; false if else
     */
    boolean fillRemaining(int i, int j) {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        //When the x and y position both reach 9 stop the function
        if (i>=N && j>=N)
            return true;

        if (i < sqrN)
        {
            if (j < sqrN)
                j = sqrN;
        }
        else if (i < N- sqrN)
        {
            if (j==(int)(i/ sqrN)* sqrN)
                j =  j + sqrN;
        }
        else
        {
            if (j == N- sqrN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    /**
     * Creates the sudoku puzzle by hiding some of the cells.
     * @return puzzle The final playable puzzle
     */
    public int[][] createPuzzle() {
        puzzle = copy(full);
        int count = hides;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N);
            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0) {
                j = j - 1;
            }
            if(i!=0) {
                i=i-1;
            }

            if (puzzle[i][j] != 0)
            {
                count--;
                puzzle[i][j] = 0;
            }
        }
        return puzzle;
    }

    /**
     * This copies the entire sudoku puzzle.
     * @param matrix Puzzle to copy
     * @return copy The copy of the sudoku puzzle
     */
    public int[][]copy(int[][]matrix) {
        int[][] copy = new int[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
        {
            copy[i] = new int[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++)
            {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }
}