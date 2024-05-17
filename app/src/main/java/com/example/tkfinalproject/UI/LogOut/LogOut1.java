package com.example.tkfinalproject.UI.LogOut;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

public class LogOut1 extends BaseActivity implements View.OnClickListener {

    Button btn;
    logoutModule module;
    UtilityClass utilityClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out1);
        btn = findViewById(R.id.logout);
        utilityClass = new UtilityClass(this);
        module = new logoutModule(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("התנתקות");
        adb.setMessage("האם את בטוח שאתה רוצה להתנתק");
        adb.setCancelable(false);
        adb.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                module.makeLogout();
            }
        });
        adb.setNegativeButton("לא", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.create().show();
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.logoutlayout2;
    }
}