package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Button m_btnLogin;
    private Button m_btnSignup;
    private EditText m_password;
    private EditText m_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        db = new DatabaseHelper(this);
        m_btnLogin = (Button) findViewById(R.id.btn_login);
        m_btnSignup = (Button) findViewById(R.id.btn_signup);
        m_email = (EditText) findViewById(R.id.signin_txt_email);
        if(email!=null){
            m_email.setText(email);
        }
        m_password = (EditText) findViewById(R.id.signin_txt_password);
        this.getSupportActionBar().setTitle("Sudoku");

        m_btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String em = m_email.getText().toString();
                String pw = m_password.getText().toString();
                //check if email is empty
                if(em.equals("")){
                    Toast.makeText(getApplicationContext(), "The email field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(pw.equals("")){
                    Toast.makeText(getApplicationContext(), "The password field is empty!", Toast.LENGTH_SHORT).show();
                }
                Boolean check = db.checkEmailAndPassword(em,pw);
                if(check==true){
                    Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    intent.putExtra("email", em);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
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
