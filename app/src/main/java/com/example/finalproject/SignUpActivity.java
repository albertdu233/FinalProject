/**
 * This class represents the sign up/registration page.
 */
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

        /**
         * Sets a listener for the apply button. Checks if all fields are valid
         * and then registers the account for the user.
         */
        sp_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = sp_email.getText().toString();
                String un = sp_username.getText().toString();
                String pw = sp_password.getText().toString();
                String re = sp_re.getText().toString();
                //checks if the email field is empty
                if(em.equals("")){
                    Toast.makeText(getApplicationContext(), "The email field is empty!", Toast.LENGTH_SHORT).show();
                }
                //hecks if the email format is correct
                else if (!emailCheck(em)) {
                    Toast.makeText(getApplicationContext(), "Email format incorrect!",
                            Toast.LENGTH_SHORT).show();
                    sp_email.setText("");
                }
                //checks if the email is already used
                else if(db.checkEmail(em)==false){
                    Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_SHORT).show();
                    sp_email.setText("");
                }
                //checks if the username field is empty
                else if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "The username field is empty!", Toast.LENGTH_SHORT).show();
                }
                //checks if the username is too short
                else if (un.length() < 3) {
                    Toast.makeText(getApplicationContext(), "The username cannot be less than 3 letters", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                //checks if the username is too long
                else if (un.length() > 13) {
                    Toast.makeText(getApplicationContext(), "The username cannot be longer than 13 characters", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                //checks if the email is already used
                else if(db.checkUser(un)==false){
                    Toast.makeText(getApplicationContext(), "This username is already registered", Toast.LENGTH_SHORT).show();
                    sp_username.setText("");
                }
                //checks if the password field is empty
                else if(pw.equals("")){
                    Toast.makeText(getApplicationContext(), "The password field is empty!", Toast.LENGTH_SHORT).show();
                }
                //checks if the length of the password is correct
                else if (pw.length() < 6) {
                    Toast.makeText(getApplicationContext(), "The password must be at least 6 letters long", Toast.LENGTH_SHORT).show();
                    sp_password.setText("");
                }
                //checks if the retype password is empty
                else if(re.equals("")){
                    Toast.makeText(getApplicationContext(), "The retype password field is empty!", Toast.LENGTH_SHORT).show();
                }
                //checks if the retype password matches the password
                else if(!re.equals(pw)){
                    Toast.makeText(getApplicationContext(), "The passwords do not match!", Toast.LENGTH_SHORT).show();
                }
                //after everything is finished, try to insert it
                else {
                    Boolean insert = db.insert(em,un,pw, R.drawable.avatar0, 0);
                    if(insert==true){
                        Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    intent.putExtra("Username", un);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Checks to see if the string is in the proper email format.
     * @param email String to be checked against email format
     * @return Properly formatted email string
     */
    boolean emailCheck(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
