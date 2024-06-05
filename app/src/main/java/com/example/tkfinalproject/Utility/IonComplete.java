package com.example.tkfinalproject.Utility;

import com.example.tkfinalproject.RePostry.User;

/**
 * IonComplete is an interface that provides callback methods to handle different types of completion events.
 */
public interface IonComplete {

    /**
     * Called when a boolean completion event occurs.
     *
     * @param flag the boolean value indicating the result of the completion event.
     */
    void onCompleteBool(boolean flag);

    /**
     * IonCompleteInt is a nested interface that provides a callback method to handle integer completion events.
     */
    interface IonCompleteInt {
        /**
         * Called when an integer completion event occurs.
         *
         * @param flag the integer value indicating the result of the completion event.
         */
        void onCompleteInt(int flag);
    }

    /**
     * IonCompleteUser is a nested interface that provides a callback method to handle user completion events.
     */
    interface IonCompleteUser {
        /**
         * Called when a user completion event occurs.
         *
         * @param user the User object indicating the result of the completion event.
         */
        void onCompleteUser(User user);
    }
}

