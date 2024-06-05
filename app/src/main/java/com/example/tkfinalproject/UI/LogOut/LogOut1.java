package com.example.tkfinalproject.UI.LogOut;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.UtilityClass;

/**
 * The LogOut1 class extends BaseActivity and implements View.OnClickListener to handle logout functionality.
 * It displays an AlertDialog asking the user to confirm logout.
 */
public class LogOut1 extends BaseActivity implements View.OnClickListener {

    /** The button that triggers the logout process. */
    Button btn;

    /** The module handling the logout logic. */
    logoutModule module;

    /** Utility class for various utility functions. */
    UtilityClass utilityClass;

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
        setContentView(R.layout.activity_log_out1);
        btn = findViewById(R.id.logout);
        utilityClass = new UtilityClass(this);
        module = new logoutModule(this);
        btn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked. This method is invoked when the user clicks the logout button.
     * It displays an AlertDialog to confirm the logout action.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("התנתקות");
        adb.setMessage("האם את בטוח שאתה רוצה להתנתק");
        adb.setCancelable(false);
        adb.setPositiveButton("כן", (dialog, which) -> module.makeLogout());
        adb.setNegativeButton("לא", (dialog, which) -> dialog.cancel());
        adb.create().show();
    }

    /**
     * Retrieves the root layout ID for the activity. This is used to set the content view.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.logoutlayout2;
    }
}