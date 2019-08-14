package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private User login;
    private ImageButton editEmail;
    private ImageButton editPassword;
    private EditText input;
    private EditText input2;
    private TextView txtUn;
    private TextView txtPw;
    private TextView txtEm;
    private AlertDialog editEm;
    private AlertDialog editPw;
    private AlertDialog logout;
    private Button save;
    private Button signout;
    private String newEm;
    private String newPw;
    private ImageView avatar;
    private int avatarId;
    private ArrayList<Integer> avatarList = new ArrayList<Integer>();

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

        editEmail = (ImageButton) findViewById(R.id.btn_editemail);
        editPassword = (ImageButton) findViewById(R.id.btn_editpassword);
        avatar = (ImageView)findViewById(R.id.image_avatar) ;
        avatar.setImageDrawable(getDrawable(login.getAvatraId()));

        avatarList.add(R.drawable.avatar0);
        avatarList.add(R.drawable.avatar1);
        avatarList.add(R.drawable.avatar2);
        avatarList.add(R.drawable.avatar3);
        avatarList.add(R.drawable.avatar4);
        avatarList.add(R.drawable.avatar5);
        avatarList.add(R.drawable.avatar6);

        initializeSpinner();

        txtUn = (TextView)findViewById(R.id.txt_p_username);
        txtPw = (TextView)findViewById(R.id.txt_p_password);
        txtEm = (TextView)findViewById(R.id.txt_p_email);
        txtUn.setText("USERNAME\n " + login.getUsername());
        txtPw.setText("PASSWORD\n " + login.getPassword());
        txtEm.setText("EMAIL\n " + login.getEmail());
        save = (Button) findViewById(R.id.btn_save);
        signout = (Button)findViewById(R.id.btn_signout);
        newEm = login.getEmail();
        newPw = login.getPassword();

        // Show the Up button in the action bar.
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set if click the arrow then go back
        //Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update email");
        builder.setMessage("Enter a new email");
        input = new EditText(ProfileActivity.this);
        builder.setView(input);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String em = input.getText().toString();
                String email = login.getEmail();

                //check if email is empty
                if(em.equals("")){
                    Toast.makeText(getApplicationContext(), "The email field is empty!", Toast.LENGTH_SHORT).show();
                }
                //Check if the email format is correct
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                    Toast.makeText(getApplicationContext(), "Email format incorrect!", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                //check if the email is already used
                else if(db.checkEmail(em)==false){
                    Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                else if(em.equals(email)){
                    Toast.makeText(getApplicationContext(), "The new email cannot be the same as the old one", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                else{
                    newEm = em;
                    txtEm.setText("EMAIL\n " + newEm);
                    Toast.makeText(getApplicationContext(), "Email updated!", Toast.LENGTH_SHORT).show();
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

        editEm = builder.create();

        //click listener for change username

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editEm.show();
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
                    newPw = pw;
                    txtPw.setText("PASSWORD\n " + newPw);
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
        editPw = builder2.create();

//click listener for change password
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPw.show();
            }
        });

//set save button

       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User update = new User(newEm, login.getUsername(), newPw,avatarId, login.getBestScore());
                db.Update(update);
                Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog.Builder builder3 = new AlertDialog.Builder(ProfileActivity.this);

//set dialog for signout

        builder3.setTitle("Confirm");
        builder3.setMessage("Are you sure you want to sign out?");
        builder3.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
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

    private void initializeSpinner() {
        int index = avatarList.indexOf(login.getAvatraId());
        final Integer numbers[] = {0,1, 2, 3, 4, 5, 6};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        Spinner spinner = findViewById(R.id.avatar_spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(index);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedNumber = numbers[i];
                avatarId = avatarList.get(selectedNumber);
                avatar.setImageDrawable(getDrawable(avatarId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
