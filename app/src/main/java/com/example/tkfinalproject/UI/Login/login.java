package com.example.tkfinalproject.UI.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.BaseActivity;

public class login extends BaseActivity implements View.OnClickListener {

    private Button btn;
    private CheckBox checkBox;
    private EditText username,userpass;
    Boolean Pveq = true;
    SharedPreferences sp;
    String Un,Up;
    loginModule Module;
    User user;
    AlertDialog.Builder adb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //לעשות תהליך של התחבורת אוטמוטית לפי הshared prefernce
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.approve);
        checkBox = findViewById(R.id.stayconnected);
        username = findViewById(R.id.usered);
        userpass = findViewById(R.id.passed);
        btn.setOnClickListener(this);
        Module = new loginModule(this);
        sp = getSharedPreferences("MyUserPerfs" , Context.MODE_PRIVATE);
        adb = new AlertDialog.Builder(this);
        userpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=userpass.getRight()-userpass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=userpass.getSelectionEnd();
                        if(Pveq){
                            userpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            userpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            Pveq=false;
                        }else {
                            userpass.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.baseline_visibility_24, 0);
                            userpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            Pveq=true;
                        }
                        userpass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.loginlayout;
    }

    @Override
    public void onClick(View v) {
        //לעשות בדיקת אינטרנט ולבדוק שהנתונים קיימים במערכת
        //ניתן להתחבר אם כבר הייתה כניסה והוא קיים בDb
        //לעשות בדיקה שהאימייל לא קיים
        btn.setEnabled(false);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        user = new User(username.getText().toString().trim(),userpass.getText().toString().trim());
        Intent intent = new Intent(login.this,FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SharedPreferences.Editor editor = sp.edit();
        if (isConnected){
                Module.UserExsist(user, new com.example.tkfinalproject.RePostry.MyFireBaseHelper.checkUser() {
                    @Override
                    public void onCheckedUser(boolean flag) {
                        if(flag)
                        {
                            Module.setUser(user);
                            Module.addUser(user);
                            if (checkBox.isChecked()){
                                Un = username.getText().toString().trim();
                                Up = userpass.getText().toString().trim();
                                editor.putString("UserName",Un);
                                editor.putString("UserPass",Up);
                                editor.commit();
                                startActivity(intent);
                            }
                            else {
                                editor.putString("UserName","");
                                editor.putString("UserPass","");
                                editor.commit();
                                startActivity(intent);
                            }
                        }
                        else {
                            adb.setTitle("יש בעיה חבר!");
                            adb.setMessage("שם משתמש או סיסמה לא נכונים");
                            adb.setCancelable(false);
                            adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            adb.create().show();
                            btn.setEnabled(true);
                        }
                    }
                });
        }
        else{
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("אין אינטרנט חבר אי אפשר להתחבר");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.create().show();
            btn.setEnabled(true);
        }
    }
}