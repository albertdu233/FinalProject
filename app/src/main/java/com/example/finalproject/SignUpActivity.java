package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Button sp_btn_apply;
    private EditText sp_password;
    private EditText sp_re;
    private EditText sp_email;
    private EditText sp_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        db = new DatabaseHelper(this);
        sp_email = (EditText)findViewById(R.id.txt_apply_email);
        sp_username = (EditText)findViewById(R.id.txt_apply_name);
        sp_password = (EditText)findViewById(R.id.txt_apply_password);
        sp_re = (EditText)findViewById(R.id.txt_apply_retype);
        sp_btn_apply = (Button)findViewById(R.id.btn_apply);

        sp_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = sp_email.getText().toString();
                String un = sp_username.getText().toString();
                String pw = sp_password.getText().toString();
                String re = sp_re.getText().toString();
                //check if email is empty
                if(em.equals("")){
                    Toast.makeText(getApplicationContext(), "The email field is empty!", Toast.LENGTH_SHORT).show();
                }
                //Check if the email format is correct
                else if (!emailCheck(em)) {
                    Toast.makeText(getApplicationContext(), "Email format incorrect!",
                            Toast.LENGTH_SHORT).show();
                    sp_email.setText("");
                }
                //check if the email is already used
                else if(db.checkEmail(em)==false){
                    Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_SHORT).show();
                    sp_email.setText("");
                }
                //check if the username is empty
                else if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "The username field is empty!", Toast.LENGTH_SHORT).show();
                }
                //check if the username is too short
                else if (un.length() < 3) {
                    Toast.makeText(getApplicationContext(), "The username cannot be less than 3 letters", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                //check if the username is too long
                else if (un.length() > 13) {
                    Toast.makeText(getApplicationContext(), "The username cannot be longer than 13 characters", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                //check if the email is already used
                else if(db.checkUser(un)==false){
                    Toast.makeText(getApplicationContext(), "This username is already registered", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                else if(pw.equals("")){
                    Toast.makeText(getApplicationContext(), "The password field is empty!", Toast.LENGTH_SHORT).show();
                }
                //check if the length of password is correct
                else if (pw.length() < 6) {
                    Toast.makeText(getApplicationContext(), "The password must be at least 6 letters long", Toast.LENGTH_SHORT).show();
                    sp_password.setText("");
                }
                //check if the retype password is empty
                else if(re.equals("")){
                    Toast.makeText(getApplicationContext(), "The retype password field is empty!", Toast.LENGTH_SHORT).show();
                }
                //check if the retype password matched the password
                else if(!re.equals(pw)){
                    Toast.makeText(getApplicationContext(), "The passwords do not match!", Toast.LENGTH_SHORT).show();
                }
                //after everything is finished, try to insert it
                else {
                    Boolean insert = db.insert(em,un,pw, 0, 0);
                    if(insert==true){
                        Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    intent.putExtra("email", em);
                    startActivity(intent);
                }
            }
        });
    }
    //Function that use the patterns that android studio has to check the email format.
    boolean emailCheck(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
