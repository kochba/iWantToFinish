package com.example.tkfinalproject.UI.RefundActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tkfinalproject.R;
import com.example.tkfinalproject.UI.FirstPage.FirstPage;
import com.example.tkfinalproject.UI.Fragments.cancelFragment;
import com.example.tkfinalproject.UI.Fragments.creditCard;
import com.example.tkfinalproject.UI.Fragments.firstFragment;
import com.example.tkfinalproject.UI.Progress.progerssFirst;
import com.example.tkfinalproject.UI.Progress.progressSecond;
import com.example.tkfinalproject.Utility.BaseActivity;
import com.example.tkfinalproject.Utility.LocaleHelper;
import com.example.tkfinalproject.Utility.Phone;

public class Refund extends BaseActivity {
//    private static final int TARGET_WIDTH = 1080 ;
//    private static final int TARGET_HEIGHT = 2200;
    FragmentManager fragmentManager;
    EditText editText;
    Phone phone;
    private Intent intent;
    boolean reset;
    int currentFragment;
    private Button button1, button2, button3, button4;
    private Fragment fragment1, fragment2, fragment3, fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        fragment1 = new creditCard();
        fragment2 = new cancelFragment();
        fragment3 = new firstFragment();
        fragment4 = new firstFragment();
        button1 = findViewById(R.id.creditbutton);
        button2 = findViewById(R.id.cancel);
        button3 = findViewById(R.id.info);
        button4 = findViewById(R.id.cash);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.VISIBLE);
//        // Get the dimensions of the current device
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int currentWidth = displayMetrics.widthPixels;
//        int currentHeight = displayMetrics.heightPixels;
//
//        // Calculate the scaling factors
//        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
//        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
//
//        // Adjust sizes and margins
//        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, firstFragment.class, null)
                .commit();
        editText = findViewById(R.id.finalprice);
        phone = (Phone) getIntent().getSerializableExtra("price");
        try {
            editText.setText(phone.getAmount() + "₪");
        } catch (Exception e) {
            intent = new Intent(Refund.this, FirstPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        button1.setOnClickListener(view -> showFragment(1));
        button2.setOnClickListener(view -> showFragment(2));
        button3.setOnClickListener(view -> showFragment(3));
        button4.setOnClickListener(view -> showFragment(4));
        reset = true;
        if (savedInstanceState != null){
            showFragment(savedInstanceState.getInt("code"));
            if (savedInstanceState.getBoolean("reset")){
                savedInstanceState.putBoolean("reset",false);
                reset = false;
                this.recreate();
            }
        }else {
            showFragment(3);
        }
    }

    public static Context getContext(){
        if (getContext() != null){
            return getContext();
        }
        else {
            return null;
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("code",currentFragment);
        outState.putBoolean("reset",reset);
    }
    @Override
    protected int getRootLayoutId() {
        return R.id.refundlayout;
    }

    //    @Override
//    public void onClick(View view) {
//
//        fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, creditCard.class,null)
//                .commit();
//    }
    private void showFragment(int code) {
        currentFragment = code;
        switch (code){
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment1)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment2)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment3)
                        .commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment4)
                        .commit();
        }

        // Update button visibility based on the current fragment
        if (code == 1) {
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 2) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 3) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.VISIBLE);
        } else if (code == 4) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.GONE);
        }
    }

//    private void adjustSizesAndMargins(float widthScaleFactor, float heightScaleFactor) {
//        LinearLayout linearLayout = findViewById(R.id.refundlayout);
//
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            View child = linearLayout.getChildAt(i);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();
//
//            // Adjust width and height
//            if (params.width != LinearLayout.LayoutParams.WRAP_CONTENT &&
//                    params.width != LinearLayout.LayoutParams.MATCH_PARENT) {
//                params.width = Math.round(params.width * widthScaleFactor);
//            }
//            if (params.height != LinearLayout.LayoutParams.WRAP_CONTENT &&
//                    params.height != LinearLayout.LayoutParams.MATCH_PARENT) {
//                params.height = Math.round(params.height * heightScaleFactor);
//            }
//
//            // Adjust margins
//            params.leftMargin = Math.round(params.leftMargin * widthScaleFactor);
//            params.rightMargin = Math.round(params.rightMargin * widthScaleFactor);
//            params.topMargin = Math.round(params.topMargin * heightScaleFactor);
//            params.bottomMargin = Math.round(params.bottomMargin * heightScaleFactor);
//
//            child.setLayoutParams(params);
//        }
//    }
}