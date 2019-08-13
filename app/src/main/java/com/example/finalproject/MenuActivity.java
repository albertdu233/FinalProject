package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private User login;
    private ImageButton menu_btn_pro;
    private Button menu_aboutus;

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
                builder.setTitle("About us!");
                builder.setMessage("Jingyan Du - 014436615\n" +
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

    }
}