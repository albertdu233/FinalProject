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

        menu_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        menu_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),RankActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        menu_btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = login.getUsername();
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("Username", un);
                startActivity(intent);
            }
        });

        menu_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Final project!");
                builder.setMessage("Group member: \n"+"Jingyan Du - 014436615\n" +
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

        menu_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Help!");
                builder.setMessage("Click the pencil to open profile page\nClick play to play sudoku\nClick ank to check leaderboard\n"+
                        "Click About us to see authors\n"+"To play sudoku, each row and column need\nto be filled by different numbers\n" +
                        "And each 3x3 group needs to be filled \n" +
                        "by different number");

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