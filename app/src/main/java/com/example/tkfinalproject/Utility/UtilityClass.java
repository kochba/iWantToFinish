package com.example.tkfinalproject.Utility;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;

/**
 * UtilityClass provides various utility methods for network connectivity checks and alert dialogs.
 * It is designed to simplify showing alert dialogs for different types of errors in the application.
 */
public class UtilityClass {
    Context myContext;
    AlertDialog.Builder adb;

    /**
     * Constructs a new UtilityClass with the specified context.
     *
     * @param context the context to use for operations.
     */
    public UtilityClass(Context context) {
        myContext = context;
        adb = new AlertDialog.Builder(context);
    }

    /**
     * Shows an alert dialog indicating that a general error has occurred.
     * The dialog will have a title, a message, and a single button to acknowledge the error.
     */
    public void showAlertExp() {
        adb.setTitle("קרתה תקלה");
        adb.setMessage("נסה שוב במועד מאוחר יותר");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {
            // Handle the positive button click
        });
        adb.create().show();
    }

    /**
     * Shows an alert dialog indicating that an internet error has occurred and prompts the user to connect to the internet.
     * If the context is an instance of UpdateUser, the user will be redirected to the FirstPage activity.
     */
    public void showAlertInternet() {
        if (!(myContext instanceof MainActivity)) {
            adb.setTitle("קרתה שגיאת אינטרנט");
            adb.setMessage("התחבר לאינטרנט ונסה שוב");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {
                if (myContext instanceof UpdateUser) {
                    Intent intent = new Intent(myContext, FirstPage.class);
                    myContext.startActivity(intent);
                }
            });
            adb.create().show();
        }
    }

    /**
     * Shows an alert dialog indicating that there is a server disconnection error.
     * The dialog will prompt the user to reconnect to the internet, restart the application, and try again.
     * If the context is an instance of Activity, the activity will be finished, and the application will exit.
     */
    public void showAlertConnection() {
        if (!(myContext instanceof MainActivity)) {
            adb.setTitle("קרתה שגיאת ניתוק מהשרת");
            adb.setMessage("מכבה את האפליקציה, התחבר לאינטרנט, הפעל אותה מחדש ונסה שוב");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {
                // Cast context to Activity and finish it
                if (myContext instanceof Activity) {
                    ((Activity) myContext).finishAffinity();
                }
                // Exit the application
                System.exit(0);
            });
            adb.create().show();
        }
    }

    /**
     * Shows an alert dialog indicating that there is an issue with the provided email address.
     * The dialog will prompt the user to provide a valid email address.
     */
    public void showAlertEmail() {
        adb.setTitle("יש בעיה חבר");
        adb.setMessage("מלא כתובת דואר אלקטרוני תקינה");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {
            // Handle the positive button click
        });
        adb.create().show();
    }

    /**
     * Shows an alert dialog indicating that there is an issue with the provided phone number.
     * The dialog will prompt the user to provide a valid phone number.
     */
    public void showAlertPhoneNumber() {
        adb.setTitle("יש בעיה חבר");
        adb.setMessage("מלא מספר טלפון תקין");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {
            // Handle the positive button click
        });
        adb.create().show();
    }
}
