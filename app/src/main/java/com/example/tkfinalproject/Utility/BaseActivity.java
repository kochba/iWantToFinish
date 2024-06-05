package com.example.tkfinalproject.Utility;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * BaseActivity is an abstract base class for activities that provides functionality to scale the layout
 * and its views according to the screen size. It also manages a loading overlay.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final int TARGET_WIDTH = 1080; // Target width for scaling factor calculation
    private static final int TARGET_HEIGHT = 2200; // Target height for scaling factor calculation

    // Scaling factors for adjusting view dimensions
    float heightScaleFactor;
    float widthScaleFactor;
    private ViewGroup mainContent;

    /**
     * Sets the content view for the activity and adjusts sizes and margins of the views within the layout.
     *
     * @param layoutResID Resource ID to be inflated, which will be set as the content view.
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mainContent = findViewById(getRootLayoutId());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int currentWidth = displayMetrics.widthPixels;
        int currentHeight = displayMetrics.heightPixels;

        // Calculate the scaling factors
        widthScaleFactor = (float) currentWidth / TARGET_WIDTH;
        heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        adjustSizesAndMargins(widthScaleFactor, heightScaleFactor, findViewById(getRootLayoutId()));
    }

    /**
     * Called when the activity is starting. Sets the locale to Hebrew.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getBaseContext(), "he");
    }

    /**
     * Shows the loading overlay by making the first child of the main content visible and the second child invisible.
     */
    protected void showLoadingOverlay() {
        mainContent.getChildAt(0).setVisibility(View.VISIBLE);
        mainContent.getChildAt(1).setVisibility(View.GONE);
        setViewsEnabled(mainContent, false);
    }

    /**
     * Hides the loading overlay by making the first child of the main content invisible and the second child visible.
     */
    public void hideLoadingOverlay() {
        mainContent.getChildAt(0).setVisibility(View.GONE);
        mainContent.getChildAt(1).setVisibility(View.VISIBLE);
        setViewsEnabled(mainContent, true);
    }

    /**
     * Enables or disables all views within the provided ViewGroup recursively.
     *
     * @param viewGroup The ViewGroup whose child views' enabled state is to be set.
     * @param enabled   True to enable the views, false to disable them.
     */
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

    /**
     * Called when the activity will start interacting with the user. Ensures any necessary actions are taken when the activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Perform any final cleanup before an activity is destroyed. Hides the loading overlay.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoadingOverlay();
    }

    /**
     * Called when the system is about to start resuming a previous activity. Ensures any necessary actions are taken when the activity pauses.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Abstract method that must be implemented by subclasses to provide the root layout ID.
     *
     * @return The root layout ID.
     */
    protected abstract int getRootLayoutId();

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

                        // Adjust drawable padding
                        int drawablePadding = Math.round(textView.getCompoundDrawablePadding() * widthScaleFactor);
                        textView.setCompoundDrawablePadding(drawablePadding);
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

                    // Adjust padding
                    int paddingLeft = Math.round(child.getPaddingLeft() * widthScaleFactor);
                    int paddingTop = Math.round(child.getPaddingTop() * heightScaleFactor);
                    int paddingRight = Math.round(child.getPaddingRight() * widthScaleFactor);
                    int paddingBottom = Math.round(child.getPaddingBottom() * heightScaleFactor);
                    child.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

                    // Adjust minimum width and height
                    int minWidth = Math.round(child.getMinimumWidth() * widthScaleFactor);
                    int minHeight = Math.round(child.getMinimumHeight() * heightScaleFactor);
                    child.setMinimumWidth(minWidth);
                    child.setMinimumHeight(minHeight);

                    // Adjust elevation
                    float elevation = child.getElevation();
                    child.setElevation(elevation * heightScaleFactor);

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

