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
        db.execSQL("Create table user(email text, username text primary key, password text, avatarid integer, bestscore integer)");
    }
    //function for upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
    }
    //function for inserting in database
    public boolean insert(String email, String username, String password, int avatarid, int bestscore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("avatarid",avatarid);
        contentValues.put("bestscore",bestscore);
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

    //checking the username and password
    public Boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?", new String[]{username, password});
        if(cursor.getCount()>0) return true; //if it is =0; it means the username and password does not match;
        else return false;
    }

    public User getUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? ",new String[]{username});
        if(cursor != null && cursor.moveToFirst() ){
            String em = cursor.getString(0);
            String un = cursor.getString(1);
            String pw = cursor.getString(2);
             int ava = Integer.parseInt(cursor.getString(3));
            int bs = Integer.parseInt(cursor.getString(4));
            return new User(em,un,pw, ava, bs);
        }
      return null;
    }

    //update data
    public boolean Update(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",user.getEmail());
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        contentValues.put("avatarid",user.getAvatraId());
        contentValues.put("bestscore",user.getBestScore());
        db.update("user",contentValues,"username=?",new String[]{user.getUsername()});
        return true;
    }
}
