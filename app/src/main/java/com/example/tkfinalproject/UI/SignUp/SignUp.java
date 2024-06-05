package com.example.tkfinalproject.UI.SignUp;

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
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.Login.login;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The SignUp class handles user sign-up functionality.
 */
public class SignUp extends BaseActivity implements View.OnClickListener {

    /** EditText fields for username, password, and password confirmation */
    private EditText name, pass, eqpass;

    /** Button for submitting the sign-up information */
    private Button btn;

    /** SharedPreferences for storing user preferences */
    SharedPreferences sp;

    /** Flag for password visibility */
    Boolean PV = true;

    /** Flag for password confirmation visibility */
    Boolean Pveq = true;

    /** Module for handling sign-up operations */
    SignUpModule signUpModule;

    /** Utility class for common utility methods */
    UtilityClass utilityClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize sign-up module and UI components
        signUpModule = new SignUpModule(this);
        btn = findViewById(R.id.Submit);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.passowrd);
        eqpass = findViewById(R.id.passowrdeq);
        btn.setOnClickListener(this);
        utilityClass = new UtilityClass(this);
        sp = getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=pass.getRight()-pass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=pass.getSelectionEnd();
                        if(PV){
//set drawable image here
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
//for hide password
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            PV=false;
                        }else {
//set drawable image here
                            pass.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.baseline_visibility_24, 0);
//for show password
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            PV=true;
                        }
                        pass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        eqpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=eqpass.getRight()-eqpass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=eqpass.getSelectionEnd();
                        if(Pveq){
//set drawable image here
                            eqpass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
//for hide password
                            eqpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            Pveq=false;
                        }else {
//set drawable image here
                            eqpass.setCompoundDrawablesRelativeWithIntrinsicBounds( 0, 0, R.drawable.baseline_visibility_24, 0);
//for show password
                            eqpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            Pveq=true;
                        }
                        eqpass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected int getRootLayoutId() {
        return R.id.signuplayout;
    }

    @Override
    public void onClick(View v) {
        // Disable button and show loading overlay during sign-up process
        btn.setEnabled(false);
        super.showLoadingOverlay();

        // Perform input validation
        if (name.getText().length() < 8) {
            // Show alert dialog for username length validation
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("שם משתמש קצר משמונה");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
            adb.create().show();
            btn.setEnabled(true);
            super.hideLoadingOverlay();
        } else if (!containsLetter(name.getText().toString())) {
            // Show alert dialog for username containing letters validation
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("בשם משתמש חייב להכיל לפחות אות אחת");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
            adb.create().show();
            btn.setEnabled(true);
            super.hideLoadingOverlay();
        } else if (pass.getText().length() < 6) {
            // Show alert dialog for password length validation
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("סיסמה קצרה מ6");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
            adb.create().show();
            btn.setEnabled(true);
            super.hideLoadingOverlay();
        } else if (!pass.getText().toString().equals(eqpass.getText().toString())) {
            // Show alert dialog for password and confirmation mismatch validation
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("יש בעיה חבר!");
            adb.setMessage("האימות והסיסמה לא זהים");
            adb.setCancelable(false);
            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
            adb.create().show();
            btn.setEnabled(true);
            super.hideLoadingOverlay();
        } else {
            // Check internet connectivity
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            Intent intent = new Intent(this, login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            if (isConnected) {
                // Perform sign-up operation if internet is available
                User user = new User(name.getText().toString().trim(), pass.getText().toString().trim());
                signUpModule.NewSignUp(user, flag -> {
                    switch (flag) {
                        case 0:
                            // Show success dialog and navigate to login screen
                            adb.setTitle("הרשמה הצליחה!");
                            adb.setMessage("אתה יכול להיכנס לאפליקצייה");
                            adb.setCancelable(false);
                            adb.setPositiveButton("הבנתי", (dialog, which) -> startActivity(intent));
                            adb.create().show();
                            break;
                        case 1:
                            // Show error dialog for failed sign-up attempt
                            adb.setTitle("הרשמה נכשלה!");
                            adb.setMessage("נסה שוב");
                            adb.setCancelable(false);
                            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
                            adb.create().show();
                            btn.setEnabled(true);
                            SignUp.super.hideLoadingOverlay();
                            break;
                        case 2:
                            // Show error dialog for existing username
                            adb.setTitle("יש בעיה חבר!");
                            adb.setMessage("השם משתמש כבר קיים במערכת");
                            adb.setCancelable(false);
                            adb.setPositiveButton("הבנתי", (dialog, which) -> {});
                            adb.create().show();
                            btn.setEnabled(true);
                            SignUp.super.hideLoadingOverlay();
                            break;
                    }
                });
            } else {
                // Show error dialog for no internet connection
                adb.setTitle("יש בעיה חבר!");
                adb.setMessage("אין אינטרנט חבר, אי אפשר להירשם");
                adb.setCancelable(false);
                adb.setPositiveButton("הבנתי", (dialog, which) -> {});
                adb.create().show();
                btn.setEnabled(true);
                super.hideLoadingOverlay();
            }
        }
    }


    /**
     * Method to check if a string contains at least one letter.
     *
     * @param input The string to check.
     * @return True if the string contains at least one letter, false otherwise.
     */
    private boolean containsLetter(String input) {
        String pattern = ".*[a-zA-Z].*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);
        return matcher.matches();
    }
}
