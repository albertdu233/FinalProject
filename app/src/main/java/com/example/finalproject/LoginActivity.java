package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ArrayList<String> emailList;
    private Button m_btnLogin;
    private Button m_btnSignup;
    private EditText m_password;
    private EditText m_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        m_btnLogin = (Button) findViewById(R.id.btn_login);
        m_btnSignup = (Button) findViewById(R.id.btn_signup);
        m_email = (EditText) findViewById(R.id.signin_txt_email);
        m_password = (EditText) findViewById(R.id.signin_txt_password);


        // the click activity for button log in
        m_btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Assign value for username from the edit text
                String uname = "";
                uname = m_email.getText().toString();
                String pword = "";
                //Assign value for password from the edit text
                pword = m_password.getText().toString();
                //Check if the dictionary contains this username
                /*if (!dictionary.containsKey(uname)) {
                    Toast.makeText(getApplicationContext(), "Cannot find this username", Toast.LENGTH_SHORT).show();
                    m_email.setText("");
                }*/
                //check if the password is empty
                 if (pword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password must be filled", Toast.LENGTH_SHORT).show();
                    m_password.setText("");
                }
                //check if the password is too short
                else if (pword.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    m_password.setText("");
                }
                //check if the password is too long
                else if (pword.length() > 8) {
                    Toast.makeText(getApplicationContext(), "Password is too long", Toast.LENGTH_SHORT).show();
                    m_password.setText("");
                }
                //check if the format and password is both correct
                //if correct, new intent will be created.
                // Welcome activity will be opened for the user, also, this activity will transfer the username for
                //welcome activity
               /* else {
                    if (dictionary.get(uname).toString().equals(pword)) {
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        intent.putExtra("User", "Welcome " + uname + "!");
                        startActivity(intent);
                    }

                }*/
            }
        });
        //click activity for sign up
        //If the user click button sign up, new intent will be created, user interface will transfer to the sign up page
        m_btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent_signup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent_signup);
            }
        });
    }
}
