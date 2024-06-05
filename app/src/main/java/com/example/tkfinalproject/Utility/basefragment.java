package com.example.tkfinalproject.Utility;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * basefragment is a base class for fragments that provides functionality to adjust the sizes and margins
 * of the views within the fragment according to the screen size.
 */
public class basefragment extends Fragment {
    private static final int TARGET_WIDTH = 1080; // Target width for scaling factor calculation
    private static final int TARGET_HEIGHT = 2200; // Target height for scaling factor calculation

    /**
     * Adjusts the sizes and margins of the provided view and its children based on the scaling factors
     * calculated from the device's screen dimensions.
     *
     * @param activity The activity context used to get the display metrics.
     * @param view The root view whose child views' properties are to be adjusted.
     */
    protected void ajustdsize(Activity activity, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate the scaling factors
        float widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        float heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor, view);
    }

    /**
     * Adjusts sizes, margins, paddings, text sizes, and other properties of views within the provided root view recursively.
     *
     * @param widthScaleFactor  The factor by which to scale the width.
     * @param heightScaleFactor The factor by which to scale the height.
     * @param rootView          The root view whose child views' properties are to be adjusted.
     */
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

                    // Uncomment if you want to adjust padding as well, except for certain views
                    /*
                    if (!(child instanceof CardForm)){
                        // Adjust padding
                        int paddingLeft = Math.round(child.getPaddingLeft() * widthScaleFactor);
                        int paddingTop = Math.round(child.getPaddingTop() * heightScaleFactor);
                        int paddingRight = Math.round(child.getPaddingRight() * widthScaleFactor);
                        int paddingBottom = Math.round(child.getPaddingBottom() * heightScaleFactor);
                        child.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                    }
                    */
                }

                // Recursively adjust sizes and margins for child views
                adjustSizesAndMargins(widthScaleFactor, heightScaleFactor, child);
            }
        }
    }
}

