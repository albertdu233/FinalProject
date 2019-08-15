package com.example.finalproject;


public class SudokuGenerator
{
    int[][]mat;//2d array that used to generator numbers
    int[][]full;//2d array that store full board
    int[][]puzzle;//2d array that store board with cells removed
    int N; // number of columns/rows.
    int sqrN; // square root of N
    int hides; // No. Of missing digits

    // Constructor
    SudokuGenerator()
    {   //N represents row and column = 9;
        this.N = 9;
        this.hides = 20;
        // Compute square root of N will be = 3
        Double SRNd = Math.sqrt(N);
        sqrN = SRNd.intValue();
        mat = new int[N][N];
    }

    // Sudoku Generator
    //This function will return a full board which will be save as a solution
    public int[][] fillValues()
    {
        // Fill the diagonal of sqrN x sqrN matrices
        fillDiagonal();
        // Fill remaining blocks
        fillRemaining(0, sqrN);
        full = copy(mat);
        return full;
    }

    // Fill the diagonal sqrN number of sqrN x sqrN matrices
    void fillDiagonal()
    {
        for (int i = 0; i<N; i=i+ sqrN){
            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
        }
    }

    //Function that checks if there is a dublicated number in a 3x3 group
    // Returns false if given 3 x 3 block contains num.

    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i< sqrN; i++)
            for (int j = 0; j< sqrN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix with random number
    void fillBox(int row,int col)
    {
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

    // Random number generator from 1-81
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    //Will make sure for a row, a column and 3x3 group, this number only appear once
    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i% sqrN, j-j% sqrN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j)
    {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
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


    // Remove the cells to a puzzle
    public int[][] createPuzzle()
    {
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

//function that I used to copy a 2d array

    public int[][]copy(int[][]matrix){
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