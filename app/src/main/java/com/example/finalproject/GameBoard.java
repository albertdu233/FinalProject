/**
 * This class represents the game board.
 */
package com.example.finalproject;

import java.util.ArrayList;

public class GameBoard {

    private int[][] gameCells = new int[9][9];
    private int cellScore = 0;

    /**
     * Default constructor
     */
    public GameBoard() {

    }

    /**
     * Sets the value for a game cell.
     * @param row The row that the cell is in
     * @param column The column that the cell is in
     * @param i The value to assign
     */
    public void setValue(int row, int column, int i) {
        gameCells[row][column] = i;
    }

    /**
     * Gets the entire gameboard matrix with its values
     * @return
     */
    public int[][] getGameCells() {
        return gameCells;
    }

    /**
     * Copies a board onto a new board. Used for the reset function.
     * @param newGameCells
     */
    public void copyBoard(int[][] newGameCells) {
        for (int i = 0; i < newGameCells.length; i++) {
            for (int j = 0; j < newGameCells[i].length; j++) {
                gameCells[i][j] = newGameCells[i][j];
            }
        }
    }

    /**
     * Checks if the board is full/completed.
     * @return true if full; false if else
     */
    public boolean isBoardFull() {
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[i].length; j++) {
                if (gameCells[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the board is correct with the solution.
     * @return True if correct; false if else
     */
    public boolean isBoardCorrect() {
        // Check horizontal if no number is the same
        for (int i = 0; i < gameCells.length; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < gameCells[i].length; j++) {
                int number = gameCells[i][j];
                if (numbers.contains(number)) {
                    return false;
                } else {
                    numbers.add(number);
                }
            }
        }

        // Check vertical if no number is the same
        for (int i = 0; i < gameCells.length; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < gameCells[i].length; j++) {
                int number = gameCells[j][i];
                if (numbers.contains(number)) {
                    return false;
                } else {
                    numbers.add(number);
                }
            }
        }

        // Check each group is in CellGroupFragment class for easier code
        // returns true if horizontal and vertical lines are correct
        return true;
    }

    /**
     * Checks if a 3x3 group is correct. If it's completed, the user will receive 200 points.
     * If not, each correct cell gets 5 points.
     * @param groupId The ID of the cell group
     * @return Returns true if correct; false if else
     */
    public boolean checkGroupCorrect(int groupId) {
        ArrayList<Integer> numbers = new ArrayList<>();
        int mistake=0;
       int row = ((groupId)/3)*3;
       int column = ((groupId)%3)*3;
       for(int i=row;i<row+3;i++){
           for(int j=column;j<column+3;j++){
               if (numbers.contains(gameCells[i][j])) {
                   mistake+=1;
               } else {
                   numbers.add(gameCells[i][j]);
               }
           }
       }
       cellScore+=(9-mistake)*5;
       if(mistake==0){
           return true;
        }
        return false;
    }

    /**
     * Checks if all the groups are correct.
     * @return Returns the total points
     */
    public int checkAllGroup(){
        int points = 0;
        for( int i=0;i<9;i++){
            if(checkGroupCorrect(i)){
                points+=200;
            }
        }

        return points+cellScore;
    }

    /**
     * Gets the value in one cell
     * @param row Row of the cell
     * @param column Column of the cell
     * @return Returns the cell with it's value
     */
    public int getValue(int row, int column) {

        return gameCells[row][column];

    }
}
