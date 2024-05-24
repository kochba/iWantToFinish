package com.example.tkfinalproject.Utility;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatEditText;

public class AutoResizeEditText extends AppCompatEditText {

    private static final float MIN_TEXT_SIZE_SP = 20f; // Minimum text size in sp
    private static final float MAX_TEXT_SIZE_SP = 32f; // Maximum text size in sp

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
        minTextSizePx = convertSpToPixels(MIN_TEXT_SIZE_SP, context);
        maxTextSizePx = convertSpToPixels(MAX_TEXT_SIZE_SP, context);

        // Add a TextWatcher to listen for text changes
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resizeText();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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

            if (bounds.width()+100 <= availableWidth) {
                break;
            }

            textSize -= 1;
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    private float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
