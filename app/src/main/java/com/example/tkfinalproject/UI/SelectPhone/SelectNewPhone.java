package com.example.tkfinalproject.UI.SelectPhone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.LogOut.LogOut1;
import com.example.tkfinalproject.UI.Progress.progerssFirst;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.CsvReader;

import java.util.List;
import java.util.Random;

/**
 * The SelectNewPhone class extends BaseActivity to handle the selection of a new phone.
 * It allows users to choose a brand, model, and capacity, and displays the corresponding price.
 */
public class SelectNewPhone extends BaseActivity implements View.OnClickListener {

    private static final int TARGET_WIDTH = 1080;
    private static final int TARGET_HEIGHT = 2200;

    /** Input fields for phone model and capacity */
    com.google.android.material.textfield.TextInputLayout inputModel, inputcapacity;

    /** EditText for maximum price display */
    EditText ed1;

    /** Random number generator */
    Random rnd;

    /** AutoCompleteTextView fields for brand, model, and capacity */
    private AutoCompleteTextView autoCompleteBrand, autoCompleteModel, autoCompleteCapacity;

    /** ImageViews for updating and logging out */
    ImageView updateicon, logouticon;

    /** Bundle for saving instance state */
    Bundle MySaved;

    /** Module for handling phone selection logic */
    selectNewPhoneMoudle moudle;

    /** CSV reader for retrieving phone data */
    CsvReader csvReader;

    /** Lists for storing brands, models, and capacities */
    List<String> brands, models, capcity;

    /** Strings for tracking text changes */
    String s1, s2;

    /** Intent for navigation */
    Intent intent;

    /** Button for confirming phone selection */
    Button btn;

    /** ArrayAdapters for model and capacity dropdowns */
    private ArrayAdapter<String> modelAdapter;
    private ArrayAdapter<String> capacityAdapter;

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
        setContentView(R.layout.activity_select_new_phone);
        MySaved = savedInstanceState;

        // Get display metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate scaling factors
        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor);

        // Initialize views
        inputModel = findViewById(R.id.inputmoudle);
        inputcapacity = findViewById(R.id.inputcapcity);
        autoCompleteBrand = findViewById(R.id.autoCompleteBrand);
        autoCompleteModel = findViewById(R.id.autoCompleteModel);
        autoCompleteCapacity = findViewById(R.id.autoCompleteCapacity);
        ed1 = findViewById(R.id.maxpriceed);
        btn = findViewById(R.id.confirmphone);
        updateicon = findViewById(R.id.updatepassnp);
        logouticon = findViewById(R.id.logouticonnp);

        // Disable model and capacity inputs initially
        inputModel.setEnabled(false);
        inputcapacity.setEnabled(false);

        // Initialize random number generator and CSV reader
        rnd = new Random();
        csvReader = new CsvReader(this);

        // Initialize module for phone selection logic
        moudle = new selectNewPhoneMoudle(this, inputModel, inputcapacity, autoCompleteBrand, autoCompleteModel, autoCompleteCapacity, btn, ed1);

        // Set click listeners
        btn.setOnClickListener(this);
        updateicon.setOnClickListener(this);
        logouticon.setOnClickListener(this);

        // Set up brand adapter
        brands = csvReader.getDistinctBrands(this);
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, brands);
        autoCompleteBrand.setAdapter(brandAdapter);

        // Brand selection listener
        autoCompleteBrand.setOnItemClickListener((parent, view, position, id) -> {
            models = csvReader.getModelsByBrand(this, autoCompleteBrand.getText().toString());
            modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, models);
            autoCompleteModel.setAdapter(modelAdapter);
            hideKeyboard();
            inputModel.setEnabled(true);
        });

        // Model selection listener
        autoCompleteModel.setOnItemClickListener((parent, view, position, id) -> {
            if (!autoCompleteBrand.getText().toString().isEmpty()) {
                capcity = csvReader.getCapcity(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString());
                capacityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, capcity);
                autoCompleteCapacity.setAdapter(capacityAdapter);
                hideKeyboard();
                inputcapacity.setEnabled(true);
            }
        });

        // Capacity selection listener
        autoCompleteCapacity.setOnItemClickListener((parent, view, position, id) -> {
            if (!autoCompleteBrand.getText().toString().isEmpty() && !autoCompleteModel.getText().toString().isEmpty()) {
                hideKeyboard();
                ed1.setText(csvReader.getprice1(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString()) + "₪");
                btn.setEnabled(true);
            }
        });

        // Add text change listeners
        autoCompleteBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                s2 = editable.toString();
                moudle.handleModelTextChanged(s1, s2, 1);
            }
        });

        autoCompleteModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                s2 = editable.toString();
                moudle.handleModelTextChanged(s1, s2, 2);
            }
        });

        autoCompleteCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                s2 = editable.toString();
                moudle.handleModelTextChanged(s1, s2, 3);
                useState();
            }
        });
    }

    /**
     * Restores the state of the activity based on the saved instance state.
     */
    private void useState() {
        if (MySaved != null) {
            if (MySaved.getBoolean("model", false)) {
                models = csvReader.getModelsByBrand(this, autoCompleteBrand.getText().toString());
                modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, models);
                autoCompleteModel.setAdapter(modelAdapter);
                hideKeyboard();
                inputModel.setEnabled(true);
            }
            if (MySaved.getBoolean("capacity", false)) {
                capcity = csvReader.getCapcity(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString());
                capacityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, capcity);
                autoCompleteCapacity.setAdapter(capacityAdapter);
                hideKeyboard();
                inputcapacity.setEnabled(true);
            }
            if (MySaved.getBoolean("btn", false)) {
                hideKeyboard();
                ed1.setText(csvReader.getprice1(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString()) + "₪");
                btn.setEnabled(true);
            }
        }
    }

    /**
     * Saves the state of the activity. This method is called before the activity may be killed
     * so that when it comes back some time in the future it can restore its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("model", inputModel.isEnabled());
        outState.putBoolean("capacity", inputcapacity.isEnabled());
        outState.putBoolean("btn", btn.isEnabled());
    }

    /**
     * Hides the keyboard if any view has focus.
     */
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Handles click events for the views.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (updateicon == v) {
            intent = new Intent(SelectNewPhone.this, UpdateUser.class);
            startActivity(intent);
        } else if (logouticon == v) {
            intent = new Intent(SelectNewPhone.this, LogOut1.class);
            startActivity(intent);
        } else {
            intent = new Intent(SelectNewPhone.this, progerssFirst.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("price", moudle.cratephoneobj());
            startActivity(intent);
        }
    }

    /**
     * Adjusts sizes and margins of the views based on scaling factors.
     *
     * @param widthScaleFactor  The scaling factor for the width.
     * @param heightScaleFactor The scaling factor for the height.
     */
    private void adjustSizesAndMargins(float widthScaleFactor, float heightScaleFactor) {
        LinearLayout linearLayout = findViewById(R.id.selectlayout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

            // Adjust width and height
            if (params.width != LinearLayout.LayoutParams.WRAP_CONTENT && params.width != LinearLayout.LayoutParams.MATCH_PARENT) {
                params.width = Math.round(params.width * widthScaleFactor);
            }
            if (params.height != LinearLayout.LayoutParams.WRAP_CONTENT && params.height != LinearLayout.LayoutParams.MATCH_PARENT) {
                params.height = Math.round(params.height * heightScaleFactor);
            }

            // Adjust margins
            params.leftMargin = Math.round(params.leftMargin * widthScaleFactor);
            params.rightMargin = Math.round(params.rightMargin * widthScaleFactor);
            params.topMargin = Math.round(params.topMargin * heightScaleFactor);
            params.bottomMargin = Math.round(params.bottomMargin * heightScaleFactor);

            child.setLayoutParams(params);
        }
    }

    /**
     * Returns the root layout ID for the activity.
     *
     * @return The root layout ID.
     */
    @Override
    protected int getRootLayoutId() {
        return R.id.selectlayout;
    }
}