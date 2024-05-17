package com.example.tkfinalproject.Utility;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tkfinalproject.R;

public abstract class BaseActivity extends AppCompatActivity{
    private static final int TARGET_WIDTH = 1080 ;
    private static final int TARGET_HEIGHT = 2200;
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate the scaling factors
        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor,findViewById(getRootLayoutId()));
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
