package com.example.tkfinalproject.Utility;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;

public class UtilityClass {
    Context Mycontext;
    AlertDialog.Builder adb;
    public UtilityClass(Context context){
        Mycontext = context;
        adb = new AlertDialog.Builder(context);
    }
    //לשים לב
    public Boolean isConected(){
        ConnectivityManager cm = (ConnectivityManager)Mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void showAlertExp(){
        adb.setTitle("קרתה תקלה");
        adb.setMessage("נסה שוב במועד מאוחר יותר");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.create().show();
    }

    public void showAlertInternet() {
        adb.setTitle("קרתה שגיאת אינטרנט");
        adb.setMessage("התחבר לאינטרנט ונסה שוב");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Mycontext instanceof UpdateUser){
                    Intent intent = new Intent(Mycontext, FirstPage.class);
                    Mycontext.startActivity(intent);
                }
            }
        });
        adb.create().show();
    }

    public void showAlertEmail() {
        adb.setTitle("יש בעיה חבר");
        adb.setMessage("מלא כתובת דואר אלקטוני תקינה");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.create().show();
    }
    public void showAlertPhoneNumber() {
        adb.setTitle("יש בעיה חבר");
        adb.setMessage("מלא מספר טלפון תקין");
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.create().show();
    }

//    public void showalert(String head,String body){
//        adb.setTitle(head);
//        adb.setMessage(body);
//        adb.setCancelable(false);
//        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//         adb.create().
//    }
}
