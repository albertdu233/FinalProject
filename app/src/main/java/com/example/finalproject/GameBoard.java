package com.example.finalproject;

import java.util.ArrayList;

public class GameBoard {

    //matrix that store the cells

    private int[][] gameCells = new int[9][9];
    private int cellScore = 0;

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

    //The function that used to copy another board, mainly for reset,  a copy will be created when a board is created, in this way we can check if the user
    //selected a valid cell

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

    //Will check a group to see if this group is finished correctlt, if it is finished, user will get 200 points, if not, user will get 5points for each number
    //that is correct

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

    //see if all groups are correct

    public int checkAllGroup(){
        int points = 0;
        for( int i=0;i<9;i++){
            if(checkGroupCorrect(i)){
                points+=200;
            }
        }

        return points+cellScore;
    }

    //Getter that used to get one cell base on x position and y position

    public int getValue(int row, int column) {

        return gameCells[row][column];

    }
}
