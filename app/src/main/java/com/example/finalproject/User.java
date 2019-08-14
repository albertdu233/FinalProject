package com.example.finalproject;

public class User {
    private String username;
    private String password;
    private String email;
    private int avatraId;
    private int bestScore;

    public User(String mail, String uname, String pword, int ava, int bs ){
        this.username = uname;
        this.password = pword;
        this.email = mail;
        this.avatraId = ava;
        this.bestScore = bs;
    }

    public User(User copy){
        this.username = copy.getUsername();
        this.password = copy.getPassword();
        this.email =copy.getEmail();
        this.avatraId = copy.getAvatraId();
        this.bestScore = copy.getBestScore();
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

    public int getAvatraId() {
        return avatraId;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setAvatraId(int avatraId) {
        this.avatraId = avatraId;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
