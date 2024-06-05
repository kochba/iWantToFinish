package com.example.tkfinalproject.UI.SignUp;


import android.content.Context;

import com.example.tkfinalproject.RePostry.Repostry;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.Utility.IonComplete;


public class SignUpModule {
    Repostry repostry;

    /**
     * Constructor for SignUpModule.
     *
     * @param context The context in which the SignUpModule is instantiated.
     */
    public SignUpModule(Context context) {
        repostry = new Repostry(context);
    }

    /**
     * Initiates the sign-up process for a new user.
     *
     * @param user             The user object containing sign-up information.
     * @param ionCompleteInt   Callback interface to handle sign-up completion.
     */
    public void NewSignUp(User user, IonComplete.IonCompleteInt ionCompleteInt) {
        repostry.RNewSignUp(user, ionCompleteInt);
    }
}

