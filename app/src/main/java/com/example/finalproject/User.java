/**
 * This class represents a user. Every user has a username, password, email,
 * avatar (represented by an avatar ID), and a best score.
 */
package com.example.finalproject;

public class User {
    private String username;
    private String password;
    private String email;
    private int avatarId;
    private int bestScore;

    /**
     * Default constructor
     * @param mail User email
     * @param uname User username
     * @param pword User password
     * @param ava User avatar
     * @param bs User best score
     */
    public User(String mail, String uname, String pword, int ava, int bs ){
        this.username = uname;
        this.password = pword;
        this.email = mail;
        this.avatarId = ava;
        this.bestScore = bs;
    }

    /**
     * Copy constructor
     * @param copy User to copy
     */
    public User(User copy){
        this.username = copy.getUsername();
        this.password = copy.getPassword();
        this.email = copy.getEmail();
        this.avatarId = copy.getAvatarId();
        this.bestScore = copy.getBestScore();
    }

    /**
     * Returns the username.
     * @return username User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username New username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     * @return password User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password New password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email.
     * @return email User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email New email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the ID for the avatar.
     * @return avatarId User's avatar ID
     */
    public int getAvatarId() {
        return avatarId;
    }

    /**
     * Returns the user's best score.
     * @return bestScore User's best score
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * Sets the ID for the avatar.
     * @param avatarId New avatar ID
     */
    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    /**
     * Sets the user's best score.
     * @param bestScore New best score
     */
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
