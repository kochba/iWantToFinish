package com.example.tkfinalproject.Utility;

/**
 * EmailCallback is an interface that provides a callback method
 * to handle the result of an email operation.
 */
public interface EmailCallback {

    /**
     * Called when the result of the email operation is available.
     *
     * @param success true if the email operation was successful, false otherwise.
     */
    void onEmailReasult(boolean success);
}

