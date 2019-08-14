package com.example.finalproject;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        g_btn_reset= (Button)findViewById(R.id.game_btn_reset);
        g_btn_newgame= (Button)findViewById(R.id.game_btn_newgame);
        g_btn_solution= (Button)findViewById(R.id.game_btn_solution);

        board = loadGameBoards();
        startBoard = new GameBoard();

        //Copy a board for reset and undo.

        startBoard.copyBoard(board.getGameCells());
        reset();

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
    }






    //Check if this cell is a preset cell
    private boolean preSet(int group, int cell) {
        int row = ((group)/3)*3 + (cell/3);
        int column = ((group)%3)*3 + ((cell)%3);
        return startBoard.getValue(row, column) != 0;
    }

    //Function that used to load game boards
    //Boards files are stoed in raw folder as txt file
    private GameBoard loadGameBoards() {
        Random rand = new Random();
        int n = rand.nextInt(3);
        int fileId=0;
        if(n==0){
            fileId =R.raw.s0;
        }
        else if(n==1){
            fileId =R.raw.s1;
        }
        else{
            fileId =R.raw.s2;
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

    public void reset(){

        int cellGroupFragments[] = new int[]{R.id.Fragment0, R.id.Fragment1, R.id.Fragment2, R.id.Fragment3,
                R.id.Fragment4, R.id.Fragment5, R.id.Fragment6, R.id.Fragment7, R.id.Fragment8};
        for (int i = 0; i < 9; i++) {
            CellGroupFragment thisCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
            thisCellGroupFragment.setGroupId(i);
        }
        //Display all values from the current board

        CellGroupFragment tempCellGroupFragment;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int column = j / 3;
                int row = i / 3;

                int fragmentNumber = (row * 3) + column;
                tempCellGroupFragment = (CellGroupFragment) getSupportFragmentManager().findFragmentById(cellGroupFragments[fragmentNumber]);
                int groupColumn = j % 3;
                int groupRow = i % 3;

                int groupPosition = (groupRow * 3) + groupColumn;
                int currentValue = board.getValue(i, j);

                tempCellGroupFragment.setValue(groupPosition, currentValue);


            }
        }
    }


//Function that ask if the user really want to leave

    public void onGoBackButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("Warning!");
        builder.setMessage("The game will be ended!");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                dialogInterface.dismiss();
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

}
