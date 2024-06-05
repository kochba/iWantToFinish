package com.example.tkfinalproject.UI.Login;

import android.content.Context;

import com.example.tkfinalproject.DB.MyFireBaseHelper;
import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;

/**
 * The loginModule class handles user authentication related operations.
 * It interacts with the Repostry class to check if a user exists,
 * set the current user, and add a new user to the database.
 */
public class loginModule {
    // Repository instance for interacting with the database
    Repostry repostry;

    /**
     * Constructor to initialize the loginModule with the application context.
     *
     * @param context The application context.
     */
    public loginModule(Context context) {
        repostry = new Repostry(context);
    }

    /**
     * Checks if the user exists in the database.
     *
     * @param user The user object containing username and password.
     * @param checkUser The callback interface for handling the result of the user existence check.
     */
    public void UserExsist(User user, MyFireBaseHelper.checkUser checkUser) {
        repostry.IsExisit(user.getUsername(), user.getPass(), checkUser);
    }

    /**
     * Sets the current user in the repository.
     *
     * @param user The user object to set as the current user.
     */
    public void setUser(User user) {
        repostry.setCurrentUser(user);
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The user object to add to the database.
     */
    public void addUser(User user) {
        repostry.addDbUser(user);
    }
}

