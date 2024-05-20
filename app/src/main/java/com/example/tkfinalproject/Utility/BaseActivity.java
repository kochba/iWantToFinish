package com.example.tkfinalproject.Utility;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tkfinalproject.R;

public abstract class BaseActivity extends AppCompatActivity{
    private static final int TARGET_WIDTH = 1080 ;
    private static final int TARGET_HEIGHT = 2200;
    private ViewGroup mainContent;
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mainContent = findViewById(getRootLayoutId());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate the scaling factors
        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor,findViewById(getRootLayoutId()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getBaseContext(), "he");
    }


    protected void showLoadingOverlay() {
        mainContent.getChildAt(0).setVisibility(View.VISIBLE);
        mainContent.getChildAt(1).setVisibility(View.GONE);
        setViewsEnabled(mainContent,false);
    }
    public void hideLoadingOverlay() {
        mainContent.getChildAt(0).setVisibility(View.GONE);
        mainContent.getChildAt(1).setVisibility(View.VISIBLE);
        setViewsEnabled(mainContent,true);
    }
    private void setViewsEnabled(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                setViewsEnabled((ViewGroup) child, enabled);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingOverlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract int getRootLayoutId();
    private void adjustSizesAndMargins(float widthScaleFactor, float heightScaleFactor,View rootView) {
        if (rootView instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) rootView;
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
}
