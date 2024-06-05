package com.example.tkfinalproject.UI.mainactivity;

import android.content.Context;

import com.example.tkfinalproject.DB.MyFireBaseHelper;
import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;

/**
 * The MainActivityModule class handles the main activity's interactions with the repository.
 * It provides methods to start the login process and set the current user.
 */
public class MainActivityModule {

    /** Repository instance to manage user data. */
    Repostry repostry;

    /**
     * Constructor for MainActivityModule. Initializes the repository.
     *
     * @param context The context of the current state of the application.
     */
    public MainActivityModule(Context context) {
        repostry = new Repostry(context);
    }

    /**
     * Initiates the login process by checking if the user exists in the repository.
     *
     * @param user The user object containing the username and password.
     * @param checkUser The callback to handle the result of the user existence check.
     */
    public void StartLogin(User user, MyFireBaseHelper.checkUser checkUser) {
        repostry.IsExisit(user.getUsername(), user.getPass(), checkUser);
    }

    /**
     * Sets the current user in the repository.
     *
     * @param user The user object to be set as the current user.
     */
    public void setUser(User user) {
        repostry.setCurrentUser(user);
    }
}

