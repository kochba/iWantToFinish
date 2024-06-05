package com.example.tkfinalproject.UI.Login;

import android.content.Context;
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

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.Utility.BaseActivity;

/**
 * The login class represents the login activity of the application.
 * It extends BaseActivity and implements View.OnClickListener and View.OnTouchListener interfaces.
 */
public class login extends BaseActivity implements View.OnClickListener {

    // UI components
    private Button btn;
    private CheckBox checkBox;
    private EditText username, userpass;
    Boolean Pveq = true;
    SharedPreferences sp;
    String Un, Up;
    loginModule Module;
    User user;
    AlertDialog.Builder adb;

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling setContentView(int) to inflate the activity's UI,
     * and using findViewById(int) to programmatically interact with widgets
     * in the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        btn = findViewById(R.id.approve);
        checkBox = findViewById(R.id.stayconnected);
        username = findViewById(R.id.usered);
        userpass = findViewById(R.id.passed);

        // Set click listener for the login button
        btn.setOnClickListener(this);

        // Initialize login module and shared preferences
        Module = new loginModule(this);
        sp = getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);

        // Initialize AlertDialog builder
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

    /**
     * Get the root layout ID of the activity.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.loginlayout;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        btn.setEnabled(false);
        super.showLoadingOverlay();

        // Check for internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // Create User object with the entered username and password
        user = new User(username.getText().toString().trim(), userpass.getText().toString().trim());
        Intent intent = new Intent(login.this, FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SharedPreferences.Editor editor = sp.edit();

        if (isConnected) {
            // Check if user exists in the system
            Module.UserExsist(user, flag -> {
                if (flag) {
                    Module.setUser(user);
                    Module.addUser(user);

                    // Save username and password if checkbox is checked
                    if (checkBox.isChecked()) {
                        Un = username.getText().toString().trim();
                        Up = userpass.getText().toString().trim();
                        editor.putString("UserName", Un);
                        editor.putString("UserPass", Up);
                    } else {
                        editor.putString("UserName", "");
                        editor.putString("UserPass", "");
                    }
                    editor.commit();
                    startActivity(intent);
                } else {
                    showErrorDialog("יש בעיה חבר!", "שם משתמש או סיסמה לא נכונים");
                    btn.setEnabled(true);
                    login.super.hideLoadingOverlay();
                }
            });
        } else {
            showErrorDialog("יש בעיה חבר!", "אין אינטרנט חבר אי אפשר להתחבר");
            btn.setEnabled(true);
            super.hideLoadingOverlay();
        }
    }

    /**
     * Shows an error dialog with the specified title and message.
     *
     * @param title The title of the dialog.
     * @param message The message of the dialog.
     */
    private void showErrorDialog(String title, String message) {
        adb.setTitle(title);
        adb.setMessage(message);
        adb.setCancelable(false);
        adb.setPositiveButton("הבנתי", (dialog, which) -> {
            // Close the dialog
        });
        adb.create().show();
    }
}
