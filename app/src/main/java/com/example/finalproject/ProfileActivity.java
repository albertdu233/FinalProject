package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private User login;
    private ImageButton editName;
    private ImageButton editPassword;
    private String newUn;
    private String newPw;
    private EditText input;
    private EditText input2;
    private TextView txtUn;
    private TextView txtPw;
    private TextView txtEm;
    private AlertDialog editN;
    private AlertDialog editP;
    private AlertDialog logout;
    private Button save;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new DatabaseHelper(this);
        //When login successfully, this class will catch the email from the login page
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        this.getSupportActionBar().setTitle("Welcome "+ username +"!");
        login = db.getUser(username);

        editName = (ImageButton) findViewById(R.id.btn_editname) ;
        editPassword = (ImageButton) findViewById(R.id.btn_editpassword) ;
        txtUn = (TextView)findViewById(R.id.txt_p_name);
        txtPw = (TextView)findViewById(R.id.txt_p_password);
        txtEm = (TextView)findViewById(R.id.txt_p_email);
        txtUn.setText(login.getUsername());
        txtPw.setText(login.getPassword());
        txtEm.setText(login.getEmail());
        save = (Button) findViewById(R.id.btn_save);
        newUn = login.getUsername();
        newPw = login.getPassword();
        signout = (Button)findViewById(R.id.btn_signout);

        // Show the Up button in the action bar.
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set if click the arrow then go back
        //Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Change username");
        builder.setMessage("Enter a new username");
        input = new EditText(ProfileActivity.this);
        builder.setView(input);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String un = input.getText().toString();
                String username = login.getUsername();
                if(un.equals("")){
                    Toast.makeText(getApplicationContext(), "New username cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                //check if the username is too short
                else if (un.length() < 3) {
                    Toast.makeText(getApplicationContext(), "The username cannot be less than 3 letters", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                //check if the username is too long
                else if (un.length() > 13) {
                    Toast.makeText(getApplicationContext(), "The username cannot be longer than 13 letters", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                else if(un.equals(username)){
                    Toast.makeText(getApplicationContext(), "The new username cannot be the same as the old one", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                else{
                    newUn=un;
                    txtUn.setText(newUn);
                    Toast.makeText(getApplicationContext(), "Username changed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                input.setText("");
                dialogInterface.dismiss();
            }
        });

        editN = builder.create();

        //click listener for change username

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editN.show();
            }
        });

        AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileActivity.this);

//set dialog for edit password

        builder2.setTitle("Change password");
        builder2.setMessage("Enter a new password");
        input2 = new EditText(ProfileActivity.this);
        builder2.setView(input2);
        builder2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String pw = input2.getText().toString();
                String password = login.getPassword();
                if(pw.equals("")){
                    Toast.makeText(getApplicationContext(), "New password cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                //check if the password length is correct
                else if (pw.length() < 6) {
                    Toast.makeText(getApplicationContext(), "The password must be at least 6 letters long", Toast.LENGTH_SHORT).show();
                    input2.setText("");
                }
                //check if the password is the same
                else if(pw.equals(password)){
                    Toast.makeText(getApplicationContext(), "The new password cannot be the same as the old one", Toast.LENGTH_SHORT).show();
                    input2.setText("");
                }
                else{
                    newPw=pw;
                    txtPw.setText(newPw);
                    Toast.makeText(getApplicationContext(), "Password changed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                input2.setText("");
                dialogInterface.dismiss();
            }
        });
        editP = builder2.create();

//click listener for change password
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editP.show();
            }
        });

//set save button

       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User update = new User(login.getEmail(),newUn, newPw);
                db.Update(update);
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog.Builder builder3 = new AlertDialog.Builder(ProfileActivity.this);

//set dialog for signout

        builder3.setTitle("Warning!");
        builder3.setMessage("Are you sure you want to sign out?");
        builder3.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("Username", login.getUsername());
                startActivity(intent);
            }
        });

        builder3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        logout = builder3.create();

       signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.show();
            }
        });
    }


    //function for the return arrow;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
