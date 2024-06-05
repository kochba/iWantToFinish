package com.example.tkfinalproject.Utility;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.tkfinalproject.UI.LastPage.lastPgae;

import java.util.Objects;

/**
 * AutoResizeEditText is a custom EditText view that automatically resizes its text to fit within its width.
 * It adjusts the text size based on the available width and height to ensure the text is always readable.
 */
public class AutoResizeEditText extends AppCompatEditText {

    // Constants for minimum and maximum text sizes in sp (scale-independent pixels)
    private static final float MIN_TEXT_SIZE_SP = 1f; // Minimum text size in sp
    private static final float MAX_TEXT_SIZE_SP = 32f; // Maximum text size in sp
    private static final int TARGET_HEIGHT = 2200; // Target height for scaling factor calculation

    // Scale factor for adjusting text size based on screen height
    float heightScaleFactor;

    // Minimum and maximum text sizes in pixels
    private float minTextSizePx;
    private float maxTextSizePx;

    /**
     * Constructor to initialize the AutoResizeEditText with a Context.
     *
     * @param context The Context in which the view is running.
     */
    public AutoResizeEditText(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor to initialize the AutoResizeEditText with a Context and AttributeSet.
     *
     * @param context The Context in which the view is running.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public AutoResizeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Constructor to initialize the AutoResizeEditText with a Context, AttributeSet, and a default style attribute.
     *
     * @param context      The Context in which the view is running.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a reference to a style resource.
     */
    public AutoResizeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initializes the view by setting up scaling factors, text sizes, and adding a TextWatcher.
     *
     * @param context The Context in which the view is running.
     */
    private void init(Context context) {
        if (context instanceof lastPgae) {
            // Get the current width and height of the screen
            lastPgae lastPgae = (lastPgae) context;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            lastPgae.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int currentHeight = displayMetrics.heightPixels;

            // Calculate the scaling factors
            heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        } else {
            heightScaleFactor = 1;
        }
        minTextSizePx = convertSpToPixels(MIN_TEXT_SIZE_SP, context);
        maxTextSizePx = convertSpToPixels(MAX_TEXT_SIZE_SP * heightScaleFactor, context);

        // Add a TextWatcher to listen for text changes
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.length() > 1) {
                    for (int i = 0; i < str.length(); i++) {
                        resizeText(str.charAt(i));
                    }
                } else {
                    resizeText();
                }
            }
        });
    }

    /**
     * Resizes the text to fit within the view's width based on a single character.
     *
     * @param str The character to be added to the current text for resizing calculations.
     */
    private void resizeText(char str) {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        String text = Objects.requireNonNull(getText()).toString() + str;

        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        Rect bounds = new Rect();
        float textSize = maxTextSizePx;

        // Measure the text size and ensure it fits within the available width
        while (textSize > minTextSizePx) {
            getPaint().setTextSize(textSize);
            getPaint().getTextBounds(text, 0, text.length(), bounds);

            if (bounds.width() < availableWidth) {
                break;
            }

            textSize -= 1;
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        while (getLineCount() > 1) {
            textSize -= 1;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * Resizes the text to fit within the view's width.
     */
    private void resizeText() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        String text = Objects.requireNonNull(getText()).toString();
        if (text.isEmpty()) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, maxTextSizePx);
            return;
        }

        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        Rect bounds = new Rect();
        float textSize = maxTextSizePx;

        // Measure the text size and ensure it fits within the available width
        while (textSize > minTextSizePx) {
            getPaint().setTextSize(textSize);
            getPaint().getTextBounds(text, 0, text.length(), bounds);

            if (bounds.width() < availableWidth) {
                break;
            }

            textSize -= 1;
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        while (getLineCount() > 1) {
            textSize -= 1;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * Converts a value in scale-independent pixels (sp) to pixels (px).
     *
     * @param sp      The value in sp to be converted.
     * @param context The Context in which the view is running.
     * @return The converted value in pixels.
     */
    private float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * Called when the size of this view has changed.
     * This method ensures the text is resized appropriately when the view size changes.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resizeText();
    }
}

