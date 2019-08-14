package com.example.finalproject;

import java.util.ArrayList;

public class GameBoard {

    //matrix that store the cells

    private int[][] gameCells = new int[9][9];

    public GameBoard() {

    }

    //setter for the game cell, need both x position and y position

    public void setValue(int row, int column, int i) {
        gameCells[row][column] = i;
    }

    //getter will return the whole matrix of game cells

    public int[][] getGameCells() {

        return gameCells;

    }

    //The function that used to copy another board

    public void copyBoard(int[][] newGameCells) {
        for (int i = 0; i < newGameCells.length; i++) {
            for (int j = 0; j < newGameCells[i].length; j++) {
                gameCells[i][j] = newGameCells[i][j];
            }
        }
    }

    //This function is used to check if the board is completed

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


    //function that used to check if this board is correct

    public boolean isBoardCorrect() {

        // Check horizontal

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

        // Check vertical

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

    //Getter that used to get one cell base on x position and y position

    public int getValue(int row, int column) {

        return gameCells[row][column];

    }

    //toString function for showing this board

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[i].length; j++) {
                if (j == 0) {
                    temp.append("\n");
                }

                int currentNumber = gameCells[i][j];
                if (currentNumber == 0) {
                    temp.append("-");
                } else {
                    temp.append(currentNumber);
                }

                if (j != (gameCells[i].length-1)) {
                    temp.append(" ");
                }
            }
        }
        return temp.toString();
    }
}
