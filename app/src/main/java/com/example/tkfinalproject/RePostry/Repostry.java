package com.example.tkfinalproject.RePostry;

import android.content.Context;

import com.example.tkfinalproject.DB.MyDataBaseHelper;
import com.example.tkfinalproject.DB.MyFireBaseHelper;
import com.example.tkfinalproject.Utility.IonComplete;

/**
 * This class acts as a repository to manage user data operations such as adding, updating,
 * and removing users. It interacts with both a local database and Firebase to perform these operations.
 */
public class Repostry {
    private final MyDataBaseHelper myDatabaseHelper;   // Helper for local database operations
    private final MyFireBaseHelper fireBaseHelper;     // Helper for Firebase operations
    private static User currentUser;                   // Current user in the system

    /**
     * Constructor to initialize the Repostry with a context.
     *
     * @param context The context to use for various operations.
     */
    public Repostry(Context context) {
        myDatabaseHelper = new MyDataBaseHelper(context);
        fireBaseHelper = new MyFireBaseHelper(context);
    }

    /**
     * Sets the current user data from Firebase and calls the provided callback upon completion.
     *
     * @param ionComplete The callback to be called upon completion.
     */
    public void setCurrentData(IonComplete ionComplete) {
        if (currentUser != null) {
            fireBaseHelper.getUserByName(currentUser.getUsername(), user -> {
                setCurrentUser(user);
                ionComplete.onCompleteBool(true);
            });
        }
    }

    /**
     * Gets the current user.
     *
     * @return The current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     *
     * @param currentUser The user to set as the current user.
     */
    public void setCurrentUser(User currentUser) {
        Repostry.currentUser = currentUser;
    }

    /**
     * Registers a new user and adds the user to both the local database and Firebase.
     * Calls the provided callback with the result of the operation.
     *
     * @param user The user to register.
     * @param ionCompleteInt The callback to be called with the result of the operation.
     */
    public void RNewSignUp(User user, IonComplete.IonCompleteInt ionCompleteInt) {
        doesUserNameExisit(user.getUsername(), flag -> {
            if (!flag) {
                if (myDatabaseHelper.AddUser(user)) {
                    fireBaseHelper.addUser(user, flag1 -> {
                        if (flag1) {
                            ionCompleteInt.onCompleteInt(0);
                        } else {
                            myDatabaseHelper.removeUser(user);
                            ionCompleteInt.onCompleteInt(1);
                        }
                    });
                } else {
                    ionCompleteInt.onCompleteInt(1);
                }
            } else {
                ionCompleteInt.onCompleteInt(2);
            }
        });
    }

    /**
     * Updates the user data in both the local database and Firebase.
     * Calls the provided callback with the result of the operation.
     *
     * @param user The user data to update.
     * @param ionCompleteInt The callback to be called with the result of the operation.
     */
    public void updatedata(User user, IonComplete.IonCompleteInt ionCompleteInt) {
        if (myDatabaseHelper.uptadePass(user)) {
            fireBaseHelper.update(user, flag -> {
                if (flag) {
                    ionCompleteInt.onCompleteInt(0);
                } else {
                    myDatabaseHelper.uptadePass(currentUser);
                    ionCompleteInt.onCompleteInt(1);
                }
            });
        } else {
            ionCompleteInt.onCompleteInt(1);
        }
    }

    /**
     * Checks if a username already exists in Firebase and calls the provided callback with the result.
     *
     * @param userName The username to check.
     * @param checkUser The callback to be called with the result.
     */
    public void doesUserNameExisit(String userName, MyFireBaseHelper.checkUser checkUser) {
        fireBaseHelper.userNameExsIts(userName, checkUser);
    }

    /**
     * Checks if a user with the given username and password exists in Firebase and calls the provided callback with the result.
     *
     * @param name The username to check.
     * @param pass The password to check.
     * @param checkUser The callback to be called with the result.
     */
    public void IsExisit(String name, String pass, MyFireBaseHelper.checkUser checkUser) {
        fireBaseHelper.userExsits(new User(name, pass), checkUser);
    }

    /**
     * Adds a user to the local database if the username does not already exist.
     *
     * @param user The user to add.
     */
    public void addDbUser(User user) {
        if (!myDatabaseHelper.DoesUserNameExisit(user.getUsername())) {
            myDatabaseHelper.AddUser(user);
        }
    }

    /**
     * Removes a user from both the local database and Firebase.
     * Calls the provided callback with the result of the operation.
     *
     * @param user The user to remove.
     * @param ionComplete The callback to be called with the result.
     */
    public void removeUser(User user, IonComplete ionComplete) {
        if (myDatabaseHelper.removeUser(user)) {
            fireBaseHelper.remove(user, flag -> {
                if (flag) {
                    ionComplete.onCompleteBool(true);
                } else {
                    myDatabaseHelper.AddUser(user);
                    ionComplete.onCompleteBool(false);
                }
            });
        } else {
            ionComplete.onCompleteBool(false);
        }
    }
}
