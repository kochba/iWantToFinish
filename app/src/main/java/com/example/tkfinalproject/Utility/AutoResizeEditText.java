package com.example.tkfinalproject.Utility;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.tkfinalproject.UI.LastPage.lastPgae;

public class AutoResizeEditText extends AppCompatEditText {

    private static final float MIN_TEXT_SIZE_SP = 1f; // Minimum text size in sp
    private static final float MAX_TEXT_SIZE_SP = 32f; // Maximum text size in sp
    private static final int TARGET_HEIGHT = 2200;
    float heightScaleFactor;

    private float minTextSizePx;
    private float maxTextSizePx;


    public AutoResizeEditText(Context context) {
        super(context);
        init(context);
    }

    public AutoResizeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoResizeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (context instanceof lastPgae) {
            // Get the current width and height of the screen
            lastPgae lastPgae = (lastPgae) context;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            lastPgae.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int currentHeight = displayMetrics.heightPixels;

            // Calculate the scaling factors
            heightScaleFactor = (float) currentHeight / TARGET_HEIGHT;
        }
        else {
            heightScaleFactor = 1;
        }
        minTextSizePx = convertSpToPixels(MIN_TEXT_SIZE_SP, context);
        maxTextSizePx = convertSpToPixels(MAX_TEXT_SIZE_SP*heightScaleFactor, context);


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
                if (str.length() > 1){
                    for (int i = 0; i < str.length(); i++) {
                        resizeText(str.charAt(i));
                    }
                }
                else {
                    resizeText();
                }
            }
        });
    }

    private void resizeText(char str) {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        String text = getText().toString()+str;
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
        while (getLineCount() > 1){
            textSize -= 1;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }
    private void resizeText() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        String text = getText().toString();
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
        while (getLineCount() > 1){
            textSize -= 1;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    private float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resizeText();
    }
}
