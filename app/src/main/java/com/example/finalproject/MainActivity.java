package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ArrayList<String> emailList;
    private Button m_btnLogin;
    private Button m_btnSignup;
    private EditText m_password;
    private EditText m_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        db = new DatabaseHelper(this);
        m_btnLogin = (Button) findViewById(R.id.btn_login);
        m_btnSignup = (Button) findViewById(R.id.btn_signup);
        m_email = (EditText) findViewById(R.id.signin_txt_email);
        m_password = (EditText) findViewById(R.id.signin_txt_password);

        m_btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        m_btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_signup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent_signup);
            }
        });
    }
}
