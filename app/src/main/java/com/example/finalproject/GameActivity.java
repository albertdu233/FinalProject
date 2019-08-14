package com.example.finalproject;
import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameActivity extends AppCompatActivity implements CellGroupFragment.OnFragmentInteractionListener {
    private GameBoard startBoard;
    private GameBoard board;
    private int clickedGroup;
    private int clickedCellId;
    private TextView clickedCell;
    private EditText input;
    private int row;
    private int column;
    private Button g_btn_reset;
    private Button g_btn_newgame;
    private Button g_btn_solution;
    private User login;
    private ArrayList<Integer> puzzles =  new ArrayList<>();
    private ArrayList<Integer> played = new ArrayList<>();
    private int puzzleid;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        g_btn_reset= (Button)findViewById(R.id.game_btn_reset);
        g_btn_newgame= (Button)findViewById(R.id.game_btn_newgame);
        g_btn_solution= (Button)findViewById(R.id.game_btn_solution);
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        login = db.getUser(username);

        //load puzzles

        puzzles.add(R.raw.s0);
        puzzles.add(R.raw.s1);
        puzzles.add(R.raw.s2);
        puzzles.add(R.raw.s3);
        puzzles.add(R.raw.s4);
        puzzles.add(R.raw.s5);
        puzzles.add(R.raw.s6);
        puzzles.add(R.raw.s7);
        puzzles.add(R.raw.s8);
        puzzles.add(R.raw.s9);

        //load game boards

        board = loadGameBoards();

        startBoard = new GameBoard();



        //Copy a board for reset and undo.

        startBoard.copyBoard(board.getGameCells());
        reset();

        //ask the user is he wants to reset the game board, will reset game board based on the copy

        g_btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("Reset game board?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        board = new GameBoard();
                        board.copyBoard(startBoard.getGameCells());
                        reset();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog editEm = builder.create();
                editEm.show();
            }
        });

        //ask the user if he wants to start a new game, will load and copy game board again

        g_btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("Start a new game?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        board = loadGameBoards();
                        startBoard.copyBoard(board.getGameCells());
                        reset();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog editEm = builder.create();
                editEm.show();
            }
        });

        //show solution, will show the correct solution for this board and show scores for this user, if the score is better than this user's best score in
        //database, then it will replacce this best score.

        g_btn_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Showing the solution");
                builder.setMessage(loadSolution());
                TextView view = new TextView(GameActivity.this);
                int point = getScore();
                view.setText("Your score is: "+point);
                builder.setView(view);
                if(login.getBestScore()<point){
                    login.setBestScore(point);
                    db.Update(login);
                }

                builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        board = loadGameBoards();
                        startBoard.copyBoard(board.getGameCells());
                        reset();
                    }
                });
                builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog editEm = builder.create();
                editEm.show();
            }
        });
    }






    //Check if this cell is a preset cell
    private boolean preSet(int group, int cell) {
        int row = ((group)/3)*3 + (cell/3);
        int column = ((group)%3)*3 + ((cell)%3);
        return startBoard.getValue(row, column) != 0;
    }

    //Function that used to load game boards
    //Boards files are stroed in raw folder as txt file
    private GameBoard loadGameBoards() {

        //totally 10 puzzles templates, each time one of them will be randomly selected, and then it will be removed from the arraylist
        //so, until the user play 10 new games, he will not meet a same puzzle.

        int fileId = 0;

        if(puzzles.size()>1){
            Random rand = new Random();
            int size = puzzles.size();
            int n = rand.nextInt(size);
            fileId=puzzles.get(n);
            puzzles.remove(n);
            played.add(fileId);
            puzzleid = fileId;
        }
        else{
            fileId=puzzles.get(0);
            puzzles.remove(0);
            played.add(fileId);
            puzzles = new ArrayList<>(played);
            played.clear();
            puzzleid = fileId;
        }
        GameBoard board = new GameBoard();
        InputStream inputStream = getResources().openRawResource(fileId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {

                // read all lines in the board
                for (int i = 0; i < 9; i++) {
                    String rowCells[] = line.split(" ");
                    for (int j = 0; j < 9; j++) {
                            board.setValue(i, j, Integer.parseInt(rowCells[j]));
                    }
                    line = bufferedReader.readLine();
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e("GameActivity", e.getMessage());
        }
        return board;
    }

//will load solution based on puzzle that currently showing

    private String loadSolution() {
        int fileId = 0;
        switch(puzzleid)
        {
            case R.raw.s0 :
                fileId = R.raw.s0s;
                break;
            case R.raw.s1 :
                fileId = R.raw.s1s;
                break;
            case R.raw.s2:
                fileId = R.raw.s2s;
                break;
            case R.raw.s3 :
                fileId = R.raw.s3s;
                break;
            case R.raw.s4 :
                fileId = R.raw.s4s;
                break;
            case R.raw.s5 :
                fileId = R.raw.s5s;
                break;
            case R.raw.s6 :
                fileId = R.raw.s6s;
                break;
            case R.raw.s7 :
                fileId = R.raw.s7s;
                break;
            case R.raw.s8 :
                fileId = R.raw.s8s;
                break;
            case R.raw.s9 :
                fileId = R.raw.s9s;
                break;
            default :
        }

        String solution = "";
        InputStream inputStream = getResources().openRawResource(fileId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                // read all lines in the board
                solution = "          "+solution+line+"\n";
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e("GameActivity", e.getMessage());
        }
        return solution;
    }

    //listener for the cell group fragment
    //will get x and y position for the game board to find the cell which the user clicked
    //And then will ask the user to enter a number
    //0 will clear the original input
    //user can only input 0-9

    @Override
    public void onFragmentInteraction(int groupId, int cellId, View view) {
        clickedCell = (TextView) view;
        clickedGroup = groupId;
        clickedCellId = cellId;
        if (!preSet(groupId, cellId)) {
             row = ((clickedGroup)/3)*3 + (clickedCellId/3);
             column = ((clickedGroup)%3)*3 + ((clickedCellId)%3);
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setTitle("Enter a number from 0-9");
            builder.setMessage("0 will clear this cell");
            input = new EditText(GameActivity.this);
            builder.setView(input);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String num = input.getText().toString();
                    //make sure the num input is integer not string
                    if(!num.matches("\\d+")){
                        input.setText("");
                        Toast.makeText(GameActivity.this, ("Invlid input!"), Toast.LENGTH_SHORT).show();
                    }
                    else if(Integer.parseInt(num)==0){
                        clickedCell.setText("");
                        clickedCell.setBackground(getDrawable(R.drawable.border_cell));
                        board.setValue(row, column, 0);
                        Toast.makeText(GameActivity.this, ("Cell cleared"), Toast.LENGTH_SHORT).show();
                    }
                    else if(Integer.parseInt(num)>=1&&Integer.parseInt(num)<=9){
                        clickedCell.setText(num);
                        clickedCell.setBackground(getDrawable(R.drawable.border_cell));
                        board.setValue(row, column, Integer.parseInt(num));
                        Toast.makeText(GameActivity.this, ("Cell filled"), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        input.setText("");
                        Toast.makeText(GameActivity.this, ("Invlid input!"), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog editEm = builder.create();
            editEm.show();
        } else {
            Toast.makeText(this, ("Can not change start piece"), Toast.LENGTH_SHORT).show();
        }
    }

    //This function will display the game board by set values for all fragments

    public void reset(){

        int cellGroupFragments[] = new int[]{R.id.Fragment0, R.id.Fragment1, R.id.Fragment2, R.id.Fragment3,
                R.id.Fragment4, R.id.Fragment5, R.id.Fragment6, R.id.Fragment7, R.id.Fragment8};
        for (int i = 0; i < 9; i++) {
            CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
            thisCellGroupFragment.setGroupId(i);
        }
        //Display all values from the current board

        CellGroupFragment displayFragment;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int column = j / 3;
                int row = i / 3;

                int fragmentNumber = (row * 3) + column;
                displayFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[fragmentNumber]);
                int groupColumn = j % 3;
                int groupRow = i % 3;

                int groupPosition = (groupRow * 3) + groupColumn;
                int currentValue = board.getValue(i, j);

                displayFragment.setValue(groupPosition, currentValue);

            }
        }
    }


    //simple function that help calculates score a user get
    public int getScore(){
        int score =0;
        if(board.isBoardFull()){
            if(board.isBoardCorrect()){
                score +=1000;
            }
        }
        score+=board.checkAllGroup();
        return score;
    }

    //function for the return arrow;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
