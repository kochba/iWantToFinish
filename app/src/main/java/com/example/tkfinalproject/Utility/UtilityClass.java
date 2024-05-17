package com.example.tkfinalproject.Utility;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

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
