package com.example.tkfinalproject.UI.LogOut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

/**
 * The logoutModule class handles the logout process for the application. It clears user data from SharedPreferences,
 * updates the repository with a null user, and starts the MainActivity.
 */
public class logoutModule {

    /** Repository instance to manage user data. */
    private final Repostry repostry;

    /** Intent to start MainActivity after logout. */
    Intent intent;

    /** Context of the current state of the application. */
    Context myContex;

    /** Utility class for various utility functions. */
    UtilityClass utilityClass;

    /** Editor for modifying SharedPreferences. */
    SharedPreferences.Editor editor;

    /** SharedPreferences to store user preferences. */
    SharedPreferences sp;

    /**
     * Constructor for logoutModule. Initializes SharedPreferences, repository, and utility class.
     *
     * @param context The context of the current state of the application.
     */
    public logoutModule(Context context) {
        myContex = context;
        sp = myContex.getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);
        editor = sp.edit();
        repostry = new Repostry(context);
        utilityClass = new UtilityClass(context);
    }

    /**
     * Executes the logout process. Clears the username and password from SharedPreferences,
     * sets the current user in the repository to null, and starts the MainActivity.
     */
    public void makeLogout() {
        editor.putString("UserName", "");
        editor.putString("UserPass", "");
        editor.commit();
        repostry.setCurrentUser(new User("", ""));
        intent = new Intent(myContex, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        myContex.startActivity(intent);
    }
}
