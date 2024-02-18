package com.example.senku;

import android.content.Context;
import android.graphics.Typeface;

public class StylizedTextView extends androidx.appcompat.widget.AppCompatTextView {

    public StylizedTextView(Context context, String textString, float textSize, int textColor, int textBackgroundColor) {
        super(context);
        int paddingAmount = 15;

        setText(textString);
        setTextSize(textSize);
        setTextColor(textColor);
        setBackgroundColor(textBackgroundColor);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setPadding(paddingAmount, paddingAmount, paddingAmount, paddingAmount);

    }
}
