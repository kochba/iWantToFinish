package com.example.tkfinalproject.UI.SelectPhone;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.LogOut.LogOut1;
import com.example.tkfinalproject.UI.Progress.progerssFirst;
import com.example.tkfinalproject.UI.UpdateUser.UpdateUser;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.CsvReader;
import com.example.tkfinalproject.Utility.LocaleHelper;
import com.example.tkfinalproject.Utility.Phone;

import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SelectNewPhone extends AppCompatActivity implements View.OnClickListener {
    private static final int TARGET_WIDTH = 1080;
    private static final int TARGET_HEIGHT = 2200;
    com.google.android.material.textfield.TextInputLayout inputModel, inputcapacity;
    EditText ed1;
    Random rnd;
    Phone phone;
    private AutoCompleteTextView autoCompleteBrand, autoCompleteModel, autoCompleteCapacity;
    ImageView updateicon, logouticon;
    selectNewPhoneMoudle moudle;
    CsvReader csvReader;
    List<String> brands, models, capcity;
    String s1, s2;
    Intent intent;
    Button btn;
    private ArrayAdapter<String> brandAdapter, modelAdapter, capacityAdapter;
    //private String[] brands = {"apple", "samsung"};
//    private String[][] models = {
//            {"iphone 15 pro max", "iphone 15 pro","iphone 15","iphone 16"},
//            {"Model B1", "Model B2"},
//    };
//    private String[][][] capacities = {
//            {{"32GB", "64GB", "128GB","256Gb"}, {"16GB", "32GB", "64GB"}, {"64GB", "128GB", "256GB"},{"64GB", "128GB", "256GB","512GB"}},
//            {{"32GB", "64GB", "128GB"}, {"16GB", "32GB", "64GB"}, {"64GB", "128GB", "256GB"}},
//            {{"32GB", "64GB", "128GB"}, {"16GB", "32GB", "64GB"}, {"64GB", "128GB", "256GB"}}
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_phone);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate the scaling factors
        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor);
        inputModel = findViewById(R.id.inputmoudle);
        inputcapacity = findViewById(R.id.inputcapcity);
        autoCompleteBrand = findViewById(R.id.autoCompleteBrand);
        autoCompleteModel = findViewById(R.id.autoCompleteModel);
        autoCompleteCapacity = findViewById(R.id.autoCompleteCapacity);
        ed1 = findViewById(R.id.maxpriceed);
        btn = findViewById(R.id.confirmphone);
        updateicon = findViewById(R.id.updatepassnp);
        logouticon = findViewById(R.id.logouticonnp);
        rnd = new Random();
        csvReader = new CsvReader(this);
        moudle = new selectNewPhoneMoudle(this, inputModel, inputcapacity, autoCompleteBrand, autoCompleteModel, autoCompleteCapacity, btn, ed1);
        btn.setOnClickListener(this);
        updateicon.setOnClickListener(this);
        logouticon.setOnClickListener(this);
        inputModel.setEnabled(false);
        inputcapacity.setEnabled(false);

        // Set up adapters for each dropdown
        brands = csvReader.getDistinctBrands(this);
        brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, brands);
        autoCompleteBrand.setAdapter(brandAdapter);


        // Brand selection listener
        autoCompleteBrand.setOnItemClickListener((parent, view, position, id) -> {
            models = csvReader.getModelsByBrand(this, autoCompleteBrand.getText().toString());
            modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, models);
            autoCompleteModel.setAdapter(modelAdapter);
//            modelAdapter.clear();
//            modelAdapter.addAll(models[position]);
            hideKeyboard();
            inputModel.setEnabled(true);
        });

        // Model selection listener
        autoCompleteModel.setOnItemClickListener((parent, view, position, id) -> {
            //int brandPosition = autoCompleteBrand.getText().toString().isEmpty() ? -1 : brandAdapter.getPosition(autoCompleteBrand.getText().toString());
            if (!autoCompleteBrand.getText().toString().isEmpty()) {
//                int modelPosition = position;
//                capacityAdapter.clear();
//                capacityAdapter.addAll(capacities[brandPosition][modelPosition]);
//                capcity = csvReader.getCapcity(this,brands.get(brandPosition),models.get(modelPosition));
                capcity = csvReader.getCapcity(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString());
                capacityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, capcity);
                autoCompleteCapacity.setAdapter(capacityAdapter);
                hideKeyboard();
                inputcapacity.setEnabled(true);
            }
        });

        autoCompleteCapacity.setOnItemClickListener((parent, view, position, id) -> {
//            int brandPosition = autoCompleteBrand.getText().toString().isEmpty() ? -1 : brandAdapter.getPosition(autoCompleteBrand.getText().toString());
//            int modelPostion = autoCompleteModel.getText().toString().isEmpty() ? -1 : modelAdapter.getPosition(autoCompleteModel.getText().toString());
            if (!autoCompleteBrand.getText().toString().isEmpty() && !autoCompleteModel.getText().toString().isEmpty()) {
//            modelAdapter.clear();
//            modelAdapter.addAll(models[position]);
                hideKeyboard();
                ed1.setText(csvReader.getprice1(this, autoCompleteBrand.getText().toString(), autoCompleteModel.getText().toString(), autoCompleteCapacity.getText().toString()) + "₪");
                btn.setEnabled(true);
            }
        });

//        // Clear model and capacity dropdowns if brand is changed
//        autoCompleteBrand.setOnDismissListener(() -> {
//            if (!flag){
//            autoCompleteModel.setText("");
//            autoCompleteCapacity.setText("");
//                inputModel.setEnabled(false);
//                inputcapacity.setEnabled(false);
//            }
//            flag = false;
//        });
//
//        // Clear capacity dropdown if model is changed
//        autoCompleteModel.setOnDismissListener(() -> {
//            if (!flag) {
//                autoCompleteCapacity.setText("");
//                inputcapacity.setEnabled(false);
//            }
//            flag = false;
//        });
        autoCompleteBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s1 = charSequence.toString();
                // This method is called before the text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;
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
                // This method is called before the text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;
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
                // This method is called before the text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                s2 = editable.toString();
                moudle.handleModelTextChanged(s1, s2, 3);
            }
        });
    }

    //    @Override
//    protected int getRootLayoutId() {
//        return R.id.selectlayout;
//    }
    private void hideKeyboard() {
        // Check if no view has focus
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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
//        } else if (btn == v) {
//            intent = new Intent(SelectNewPhone.this, SelectNewPhone.class);
//            startActivity(intent);
//        }
    }

    private void adjustSizesAndMargins(float widthScaleFactor, float heightScaleFactor) {
        LinearLayout linearLayout = findViewById(R.id.selectlayout);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

            // Adjust width and height
            if (params.width != LinearLayout.LayoutParams.WRAP_CONTENT &&
                    params.width != LinearLayout.LayoutParams.MATCH_PARENT) {
                params.width = Math.round(params.width * widthScaleFactor);
            }
            if (params.height != LinearLayout.LayoutParams.WRAP_CONTENT &&
                    params.height != LinearLayout.LayoutParams.MATCH_PARENT) {
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
}