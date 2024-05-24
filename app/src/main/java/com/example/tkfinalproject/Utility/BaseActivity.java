package com.example.tkfinalproject.Utility;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tkfinalproject.R;

public abstract class BaseActivity extends AppCompatActivity{
    private static final int TARGET_WIDTH = 1080;
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
    private void adjustSizesAndMargins(float widthScaleFactor, float heightScaleFactor, View rootView) {
        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                ViewGroup.LayoutParams params = child.getLayoutParams();

                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
                    // Adjust text size if the child is a TextView or its subclass
                    if (child instanceof TextView) {
                        TextView textView = (TextView) child;
                        float textSize = textView.getTextSize(); // getTextSize() returns the size in pixels
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * heightScaleFactor);
                    }

                    // Adjust width and height
                    if (marginParams.width != ViewGroup.LayoutParams.WRAP_CONTENT &&
                            marginParams.width != ViewGroup.LayoutParams.MATCH_PARENT) {
                        marginParams.width = Math.round(marginParams.width * widthScaleFactor);
                    }
                    if (marginParams.height != ViewGroup.LayoutParams.WRAP_CONTENT &&
                            marginParams.height != ViewGroup.LayoutParams.MATCH_PARENT) {
                        marginParams.height = Math.round(marginParams.height * heightScaleFactor);
                    }

                    // Adjust margins
                    marginParams.leftMargin = Math.round(marginParams.leftMargin * widthScaleFactor);
                    marginParams.rightMargin = Math.round(marginParams.rightMargin * widthScaleFactor);
                    marginParams.topMargin = Math.round(marginParams.topMargin * heightScaleFactor);
                    marginParams.bottomMargin = Math.round(marginParams.bottomMargin * heightScaleFactor);

                    child.setLayoutParams(marginParams);
                    // Adjust width and height of buttons with wrap_content considering weight
                    if (child instanceof Button && params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                if (child.getWidth() > 0 && child.getHeight() > 0) {
                                    int newHeight = Math.round(child.getHeight() * heightScaleFactor);
                                    // Adjust margins
                                    marginParams.leftMargin = Math.round(marginParams.leftMargin * widthScaleFactor);
                                    marginParams.rightMargin = Math.round(marginParams.rightMargin * widthScaleFactor);
                                    marginParams.topMargin = Math.round(marginParams.topMargin * heightScaleFactor);
                                    marginParams.bottomMargin = Math.round(marginParams.bottomMargin * heightScaleFactor);
                                    TextView textView = (TextView) child;
                                    float textSize = textView.getTextSize(); // getTextSize() returns the size in pixels
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * heightScaleFactor);
                                    marginParams.height = newHeight;
                                    child.setLayoutParams(marginParams);
                                    child.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            }
                        });
                    }
                }

                // Recursively adjust sizes and margins for child views
                adjustSizesAndMargins(widthScaleFactor, heightScaleFactor, child);
            }
        }
    }



}
