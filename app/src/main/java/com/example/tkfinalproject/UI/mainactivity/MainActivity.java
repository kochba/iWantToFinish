package com.example.tkfinalproject.UI.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.RePostry.User;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.Login.login;
import com.example.tkfinalproject.UI.SignUp.SignUp;
import com.example.tkfinalproject.Utility.BaseActivity;

/**
 * MainActivity class extends BaseActivity and implements View.OnClickListener to handle user login and sign-up actions.
 * It manages the initial user authentication check and redirects to the appropriate activity based on user interaction.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /** Button to initiate login process. */
    private Button log;

    /** Button to initiate sign-up process. */
    private Button sin;

    /** Module handling main activity logic. */
    MainActivityModule mainActivityModule;

    /** Intent to start another activity. */
    Intent intent;

    /** User object representing the current user. */
    User user;

    /** SharedPreferences to store user preferences. */
    SharedPreferences sp;

    /**
     * Called when the activity is first created. This is where you should do all of your normal static set up:
     * create views, bind data to lists, etc. This method also provides you with a Bundle containing the activity's
     * previously frozen state, if there was one.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this
     *                           Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           <b>Note: Otherwise it is null.</b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        closeContextMenu();
        log = findViewById(R.id.login);
        sin = findViewById(R.id.signup);
        mainActivityModule = new MainActivityModule(this);
        intent = new Intent(MainActivity.this, FirstPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sp = getApplicationContext().getSharedPreferences("MyUserPerfs", Context.MODE_PRIVATE);
        user = new User(sp.getString("UserName", ""), sp.getString("UserPass", ""));
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            super.showLoadingOverlay();
            mainActivityModule.StartLogin(user, flag -> {
                if (flag) {
                    MainActivity.super.hideLoadingOverlay();
                    mainActivityModule.setUser(user);
                    startActivity(intent);
                } else {
                    MainActivity.super.hideLoadingOverlay();
                    setClickTrue();
                }
            });
        } else {
            setClickTrue();
        }
        log.setOnClickListener(this);
        sin.setOnClickListener(this);
    }

    /**
     * Retrieves the root layout ID for the activity. This is used to set the content view.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.mainlayout;
    }

    /**
     * Enables the login and sign-up buttons.
     */
    private void setClickTrue() {
        log.setEnabled(true);
        sin.setEnabled(true);
    }

    /**
     * Called when a view has been clicked. This method is invoked when the user clicks the login or sign-up button.
     * It starts the appropriate activity based on which button was clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view == log) {
            intent = new Intent(MainActivity.this, login.class);
        } else {
            intent = new Intent(MainActivity.this, SignUp.class);
        }
        startActivity(intent);
    }
}
