package com.example.senku;

import android.content.Context;

public class ScoreBox extends StylizedTextView{
    private int scoreValue;


    public ScoreBox(Context context, float textSize, int textColor, int textBackgroundColor, int scoreValue) {
        super(context, textSize, textColor, textBackgroundColor);
        this.scoreValue = scoreValue;
        this.setText(String.valueOf(scoreValue));

    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue += scoreValue;
        this.setText(String.valueOf(this.scoreValue));

    }
}



