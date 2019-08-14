package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Button m_btnLogin;
    private Button m_btnSignup;
    private EditText m_txtUsername;
    private EditText m_txtPassword;
    private Checkable m_check;
    private SharedPreferences sp;
    //testing commit - michell

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Intent intent = getIntent();
        String user = intent.getStringExtra("Username");
        db = new DatabaseHelper(this);

        m_btnLogin = (Button) findViewById(R.id.btn_login);
        m_btnSignup = (Button) findViewById(R.id.btn_signup);
        m_txtUsername = (EditText) findViewById(R.id.signin_txt_username);
        m_txtPassword = (EditText) findViewById(R.id.signin_txt_password);
        m_check = (CheckBox) findViewById(R.id.checkBox);
        sp = getSharedPreferences("login",MODE_PRIVATE);

       if(user != null){
            m_txtUsername.setText(user);
            m_check.setChecked(false);
            sp.edit().putBoolean("logged", false).apply();
            sp.edit().remove("username").apply();;
        }

        if(sp.getBoolean("logged",false)){
            Intent logintent = new Intent(getApplicationContext(),MenuActivity.class);
            String login = sp.getString("username","");
            logintent.putExtra("Username", login);
            startActivity(logintent);
        }

        this.getSupportActionBar().setTitle("Sudoku");

        m_btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String un = m_txtUsername.getText().toString();
                String pw = m_txtPassword.getText().toString();
                //check if email is empty
                if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "The username field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(pw.equals("")){
                    Toast.makeText(getApplicationContext(), "The password field is empty!", Toast.LENGTH_SHORT).show();
                }
                Boolean check = db.checkUsernameAndPassword(un, pw);
                if(check==true){
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    if(m_check.isChecked()){
                        sp.edit().putBoolean("logged", true).apply();
                        sp.edit().putString("username",un).apply();
                    }
                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    intent.putExtra("Username", un);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
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
