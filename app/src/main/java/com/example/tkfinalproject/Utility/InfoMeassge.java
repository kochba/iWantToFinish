package com.example.tkfinalproject.Utility;

import android.content.Context;

import com.example.tkfinalproject.RePostry.Repostry;

/**
 * InfoMeassge is a class that encapsulates information about a message, including the user's name,
 * phone details, and the method used.
 */
public class InfoMeassge {
    String name;
    Phone phone;
    String Method;
    Repostry repostry;

    /**
     * Constructs a new InfoMeassge with the specified phone, method, and context.
     * Initializes the repository and sets the current user's name.
     *
     * @param phone the phone details associated with the message.
     * @param method the method used for the message.
     * @param context the context to use for initializing the repository.
     */
    public InfoMeassge(Phone phone, String method, Context context) {
        repostry = new Repostry(context);
        this.name = repostry.getCurrentUser().getUsername();
        this.phone = phone;
        this.Method = method;
    }

    /**
     * Retrieves the name associated with this message.
     *
     * @return the name associated with this message.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the method used for this message.
     *
     * @return the method used for this message.
     */
    public String getMethod() {
        return Method;
    }

    /**
     * Retrieves the phone details associated with this message.
     *
     * @return the phone details associated with this message.
     */
    public Phone getPhone() {
        return phone;
    }
}
