package com.example.senku;

import android.content.Context;
import android.graphics.Typeface;

public class StylizedTextView extends androidx.appcompat.widget.AppCompatTextView {

    public StylizedTextView(Context context, float textSize, int textColor, int textBackgroundColor) {
        super(context);
        setTextSize(textSize);
        setTextColor(textColor);
        setBackgroundColor(textBackgroundColor);
    }
}
