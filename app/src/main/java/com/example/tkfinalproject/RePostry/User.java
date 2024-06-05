package com.example.tkfinalproject.RePostry;

/**
 * This class represents a user with a username and password.
 * It provides methods to get and set the username, and get the password.
 */
public class User {
    private String username;
    private final String pass;

    /**
     * Constructor to initialize the User object with a username and password.
     *
     * @param username The username of the user.
     * @param pass The password of the user.
     */
    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPass() {
        return pass;
    }
}
