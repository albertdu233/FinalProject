package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }
    //function for create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email text primary key, username text, password text)");
    }
    //function for upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
    }
    //function for inserting in database
    public boolean insert(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("username",username);
        contentValues.put("password",password);
        long ins = db.insert("user",null, contentValues);
        if(ins==-1) return false;
        else return true;
    }

    //checking if email exists
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if(cursor.getCount()>0) return false; //if it is =0; it means the email does not exist.
        else return true;
    }
    //checking if username exists
    public Boolean checkUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if(cursor.getCount()>0) return false; //if it is =0; it means the username does not exist.
        else return true;
    }
    //checking the email and password
    public Boolean checkEmailAndPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});
        if(cursor.getCount()>0) return true; //if it is =0; it means the email and password does not match;
        else return false;
    }

    public User getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? ",new String[]{email});
        if(cursor != null && cursor.moveToFirst() ){
            String em = cursor.getString(0);
            String un = cursor.getString(1);
            String pw = cursor.getString(2);
            return new User(em,un,pw);
        }
      return null;
    }
}
