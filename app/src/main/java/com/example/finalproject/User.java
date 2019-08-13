package com.example.finalproject;

public class User {
    private String username;
    private String password;
    private String email;

    public User(String mail, String uname, String pword ){
        this.username = uname;
        this.password = pword;
        this.email = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
