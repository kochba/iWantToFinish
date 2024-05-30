package com.example.tkfinalproject.UI.UpdateUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.Login.login;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.IonComplete;
import com.example.tkfinalproject.Utility.LocaleHelper;

public class UpdateUser extends BaseActivity implements View.OnClickListener {

    EditText editTextName,editTextPass;
    Intent intent;
    Button Update;
    UpdateUserMoudle moudle;
    SharedPreferences sp;
    AlertDialog.Builder adb;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        //LocaleHelper.setLocale(this, "iw");
        editTextName = findViewById(R.id.userup);
        editTextPass = findViewById(R.id.passup);
        Update = findViewById(R.id.uptade);
        Update.setOnClickListener(this);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            moudle = new UpdateUserMoudle(this);
            moudle.showdata(editTextName,editTextPass,Update);
        }
        else {
            showalert("יש בעיה חבר!","אין אינטרנט חבר אי אפשר לעדכן סיסמה",new Intent(this,FirstPage.class));
        }
        intent = new Intent(UpdateUser.this, FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sp = getSharedPreferences("MyUserPerfs" , Context.MODE_PRIVATE);
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.updatelayout;
    }
    public void setClickTrue(){
        Update.setClickable(true);
    }

    private void showalert(String head,String body){
        adb = new AlertDialog.Builder(this);
        adb.setTitle(head);
        adb.setMessage(body);
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.create().show();
    }
    private void showalert(String head,String body,Intent intent){
        adb = new AlertDialog.Builder(this);
        adb.setTitle(head);
        adb.setMessage(body);
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        });
        adb.create().show();
    }

    @Override
    public void onClick(View view) {
        Update.setEnabled(false);
        super.showLoadingOverlay();
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        SharedPreferences.Editor editor = sp.edit();
        if (editTextName.getText().length() < 8){
            showalert("יש בעיה חבר!","שם משתמש קצר משמונה");
            Update.setEnabled(true);
            super.hideLoadingOverlay();
            editTextName.setEnabled(false);
        }
        else if (editTextPass.getText().length() < 6){
            showalert("יש בעיה חבר!","סיסמה קצרה מ6");
            Update.setEnabled(true);
            super.hideLoadingOverlay();
            editTextName.setEnabled(false);
        }
        else if (isConnected){
            user = new User(sp.getString("UserName",""),sp.getString("UserPass",""));
            moudle.updateUser(editTextName, editTextPass, new IonComplete.IonCompleteInt() {
                @Override
                public void onCompleteInt(int flag) {
                    switch (flag){
                        case 0:
                            if (user.getUsername() != ""){
                                editor.putString("UserName",editTextName.getText().toString().trim());
                                editor.putString("UserPass",editTextPass.getText().toString().trim());
                                editor.commit();
                            }
                            moudle.setUser(editTextName,editTextPass);
                            showalert("עדכון סיסמה הצליח!","אתה יכול להמשיך להשתמש באפלייקצייה",intent);
                            Update.setEnabled(true);
                            UpdateUser.super.hideLoadingOverlay();
                            editTextName.setEnabled(false);
                            break;
                        case 1:
                            showalert("העדכון נכשל!","נסה שוב");
                            Update.setEnabled(true);
                            UpdateUser.super.hideLoadingOverlay();
                            editTextName.setEnabled(false);
                            break;
                        case 2:
                            showalert("יש בעיה חבר!","השם משתמש החדש כבר קיים במערכת");
                            Update.setEnabled(true);
                            UpdateUser.super.hideLoadingOverlay();
                            editTextName.setEnabled(false);
                    }
                }
            });
        }
        else {
            showalert("יש בעיה חבר!","אין אינטרנט חבר אי אפשר לעדכן סיסמה");
            Update.setEnabled(true);
            super.hideLoadingOverlay();
            editTextName.setEnabled(false);
        }
    }

}