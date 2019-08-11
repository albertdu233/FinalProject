package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private User login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        login = db.getUser(email);
        this.getSupportActionBar().setTitle(login.getUsername());
    }

}
