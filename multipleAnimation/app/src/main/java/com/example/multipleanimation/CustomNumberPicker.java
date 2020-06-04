package com.example.multipleanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class CustomNumberPicker extends NumberPicker {
    public CustomNumberPicker(Context context) {
        super(context);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }
}
