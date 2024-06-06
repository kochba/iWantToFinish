package com.example.tkfinalproject.UI.SignUp;


import android.content.Context;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.IonComplete;


/**
 * The SignUpModule class handles the sign-up operations.
 * It communicates with the Repostry to perform user registration.
 */
public class SignUpModule {

    /** Repository to handle data operations. */
    private final Repostry repostry;

    /**
     * Constructor to initialize the SignUpModule with the given context.
     *
     * @param context The context used to initialize the repository.
     */
    public SignUpModule(Context context) {
        repostry = new Repostry(context);
    }

    /**
     * Registers a new user by invoking the repository method for sign-up.
     *
     * @param user The User object containing the user's information.
     * @param ionCompleteInt The callback interface to handle the completion of the sign-up process.
     */
    public void NewSignUp(User user, IonComplete.IonCompleteInt ionCompleteInt) {
        repostry.RNewSignUp(user, ionCompleteInt);
    }
}


