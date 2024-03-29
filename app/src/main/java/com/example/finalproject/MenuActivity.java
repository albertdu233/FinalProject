/**
 * This class represents the main menu page. It shows after a user logs in, and allows
 * the user to navigate to the profile page, play, about us, help, and the rank board.
 */
package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MenuActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private User login;
    private ImageButton menu_btn_pro;
    private Button menu_aboutus;
    private Button menu_play;
    private Button menu_rank;
    private Button menu_help;
    private TextView menu_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        login = db.getUser(username);
        this.getSupportActionBar().setTitle("Welcome " + username + "!");
        menu_btn_pro = (ImageButton) findViewById(R.id.btn_profile);
        menu_aboutus = (Button) findViewById(R.id.btn_aboutus);
        menu_play = (Button) findViewById(R.id.btn_play);
        menu_rank = (Button) findViewById(R.id.btn_rank);
        menu_help= (Button) findViewById(R.id.btn_help);
        menu_score = (TextView)findViewById(R.id.txt_bestscore);
        menu_score.setText("Best score: "+login.getBestScore());
        menu_score.setTextSize(25);
        menu_score.setTextColor(Color.rgb(255,215,0));

        /**
         * Sets a listener for the play button. Takes the user to the sudoku game board.
         */
        menu_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        /**
         * Sets a listener for the rank board button. Shows the ranks of players
         * based on best scores.
         */
        menu_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),RankActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        /**
         * Sets a listener for the profile button. Users can edit account information
         * and sign out here.
         */
        menu_btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        /**
         * Sets a listener for the about us button.
         */
        menu_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Final project: Sudoku");
                builder.setMessage("Group members: \n"+"Jingyan Du - 014436615\n" +
                        "Michell Kuang - 013421094\n" +
                        "Kishan Sarvaiya - 015658251");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
         * Sets a listener for the help button.
         */
        menu_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Help");
                builder.setMessage("- Click the pencil to open your profile page\n- Click Play to start a game\n- Click Rank to check the leaderboard\n"+
                        "- Click About us to see the authors\n\n"+"Rules: The classic Sudoku game involves a 9x9 grid with 81 cells. Each of the nine " +
                        "blocks has to contain all the numbers 1-9 within its cells. Each number can only appear once in a row, column or box.");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
}