/**
 * This class represents the game activity for the Sudoku gameplay. It controls
 * the actions for the user to insert numbers and show the solution, reset the game,
 * and start a new game.
 */
package com.example.finalproject;

import java.util.ArrayList;
import java.util.Random;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements CellGroupFragment.OnFragmentInteractionListener {
    private GameBoard startBoard;
    private GameBoard board;
    private GameBoard solutionBoard;
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
    private TextView showScore;
    private DatabaseHelper db;
    private SudokuGenerator sk;
    private int point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        g_btn_reset= (Button)findViewById(R.id.game_btn_reset);
        g_btn_newgame= (Button)findViewById(R.id.game_btn_newgame);
        g_btn_solution= (Button)findViewById(R.id.game_btn_solution);
        showScore = (TextView)findViewById(R.id.txt_score);
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        login = db.getUser(username);


        //load game boards
         sk = new SudokuGenerator();
        solutionBoard = new GameBoard();
        solutionBoard.copyBoard(sk.fillValues());
        board = new GameBoard();
        board.copyBoard(sk.createPuzzle());
        //Copy a board for reset and undo.
        startBoard = new GameBoard();
        startBoard.copyBoard(board.getGameCells());

        reset();

        /**
         * Sets a listener for the reset button that allows the user to reset the current game board.
         */
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
                        point=0;
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

        /**
         * Sets a listener for the new game button that allows the user to start a new game.
         */
        g_btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("Start a new game?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sk = new SudokuGenerator();
                        solutionBoard = new GameBoard();
                        startBoard = new GameBoard();
                        board = new GameBoard();
                        solutionBoard.copyBoard(sk.fillValues());
                        board.copyBoard(sk.createPuzzle());
                        startBoard.copyBoard(board.getGameCells());
                        g_btn_reset.setVisibility(View.VISIBLE);
                        showScore.setVisibility(View.INVISIBLE);
                        point=0;
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

        /**
         * Sets a listener for the solution button to show the user the solution for the game.
         * This will also end the game and record the player's score.
         */
        g_btn_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScore.setVisibility(View.VISIBLE);
                point = 0+ getScore();
                showScore.setText("Your score is: "+point);
                showScore.setTextSize(35);
                showScore.setTextColor(Color.YELLOW);
                showSolution();
                g_btn_reset.setVisibility(View.INVISIBLE);
                if(point>login.getBestScore()){
                    login.setBestScore(point);
                    db.Update(login);
                }

            }
        });
    }

    /**
     * Checks if the cell is a preset cell.
     * @param group The group in which the cell exists
     * @param cell The cell to check.
     * @return true if successful; false if else
     */
    private boolean preSet(int group, int cell) {
        int row = ((group)/3)*3 + (cell/3);
        int column = ((group)%3)*3 + ((cell)%3);
        return startBoard.getValue(row, column) != 0;
    }

    /**
     * Controls the functionality for gameplay. It will get the x and y position for
     * the cell that the user clicks and ask the user for a number to enter. 0 will clear
     * the original input.
     * @param groupId
     * @param cellId
     * @param view
     */
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
            /**
             * Sets a listener for the Ok button that will confirm the number selected
             */
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
            /**
             * Sets a listener for the cancel button if the user decides not to put a number there
             */
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

    /**
     * Resets and displays the game board by set values for all fragments.
     */
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

    /**
     * Shows the solution for the current game board.
     */
    public void showSolution(){

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
                int currentValue = solutionBoard.getValue(i, j);

                displayFragment.showResult(groupPosition, currentValue, solutionBoard.getValue(i, j), board.getValue(i,j));

            }
        }
    }
    /**
     * Calculates the player's score for the current game.
     * @return
     */
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

    /**
     * Fucntionality for the return button.
     * @param item Item that is selected
     * @return Returns to the previous page
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
            intent.putExtra("Username", login.getUsername());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
