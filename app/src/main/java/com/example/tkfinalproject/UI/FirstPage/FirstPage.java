package com.example.tkfinalproject.UI.FirstPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LogOut.LogOut1;
import com.example.tkfinalproject.UI.SelectPhone.SelectNewPhone;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.Utility.BaseActivity;

/**
 * The FirstPage class represents the initial screen of the application.
 * It extends BaseActivity and implements View.OnClickListener to handle
 * click events on various UI elements.
 */
public class FirstPage extends BaseActivity implements View.OnClickListener {
    // Declare UI elements
    ImageView updateicon, logouticon;
    Intent intent;
    Button btn;

    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up:
     * create views, bind data to lists, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        // Initialize UI elements
        updateicon = findViewById(R.id.updatepass);
        logouticon = findViewById(R.id.logouticon);
        btn = findViewById(R.id.newphone);

        // Set click listeners
        btn.setOnClickListener(this);
        updateicon.setOnClickListener(this);
        logouticon.setOnClickListener(this);

        // Set background resource for button
        btn.setBackgroundResource(R.drawable.button);
    }

    /**
     * Returns the root layout ID of the activity.
     * This method is overridden from BaseActivity.
     *
     * @return the root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.Firstpagelayout;
    }

    /**
     * Handles click events for the activity's UI elements.
     * This method is called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // Determine which view was clicked and handle the event accordingly
        if (updateicon == v) {
            intent = new Intent(FirstPage.this, UpdateUser.class);
            startActivity(intent);
        } else if (logouticon == v) {
            intent = new Intent(FirstPage.this, LogOut1.class);
            startActivity(intent);
        } else {
            intent = new Intent(FirstPage.this, SelectNewPhone.class);
            startActivity(intent);
        }
    }
}
