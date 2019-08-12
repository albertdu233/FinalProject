package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        login = db.getUser(email);
        this.getSupportActionBar().setTitle("Welcome "+login.getUsername()+"!");
        menu_btn_pro = (ImageButton) findViewById(R.id.btn_profile);
        menu_btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = login.getEmail();
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("email", em);
                startActivity(intent);
            }
        });

    }
}