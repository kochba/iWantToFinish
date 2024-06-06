package com.example.tkfinalproject.Utility;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.UI.mainactivity.MainActivity;

/**
 * UtilityClass provides various utility methods for network connectivity checks and alert dialogs.
 */
public class UtilityClass {
    Context Mycontext;
    AlertDialog.Builder adb;

    /**
     * Constructs a new UtilityClass with the specified context.
     *
     * @param context the context to use for operations.
     */
    public UtilityClass(Context context) {
        Mycontext = context;
        adb = new AlertDialog.Builder(context);
    }

    /**
     * Checks if the device is connected to the internet.
     *
     * @return true if the device is connected or connecting to the internet, false otherwise.
     */
    public Boolean isConected() {
        ConnectivityManager cm = (ConnectivityManager) Mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Shows an alert dialog indicating that an error has occurred.
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
     */
    public void showAlertInternet() {
        if (!(Mycontext instanceof MainActivity)) {
            adb.setTitle("קרתה שגיאת אינטרנט");
            adb.setMessage("התחבר לאינטרנט ונסה שוב");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {
                if (Mycontext instanceof UpdateUser) {
                    Intent intent = new Intent(Mycontext, FirstPage.class);
                    Mycontext.startActivity(intent);
                }
            });
            adb.create().show();
        }
    }

    /**
     * Shows an alert dialog indicating that there is an issue with the provided email address.
     */
    public void showAlertEmail() {
        adb.setTitle("יש בעיה חבר");
        adb.setMessage("מלא כתובת דואר אלקטוני תקינה");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {
            // Handle the positive button click
        });
        adb.create().show();
    }

    /**
     * Shows an alert dialog indicating that there is an issue with the provided phone number.
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
