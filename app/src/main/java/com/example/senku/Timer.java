package com.example.senku;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class Timer extends StylizedTextView {
    private boolean runTimer = true;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int m = 0, s = 0;

    public Timer(Context context, float textSize, int textColor, int textBackgroundColor) {
        super(context, String.format("%02d:%02d", 0, 0), textSize, textColor, textBackgroundColor);
    }

    public void startTimer() {
        runTimer = true;
        updateTimer();
    }

    public void stopTimer() {
        runTimer = false;
    }

    private void updateTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Increment seconds
                s++;
                // When seconds reach 60, increment minutes and reset seconds
                if (s == 60) {
                    m++;
                    s = 0;
                }
                // Reset timer after 59 minutes
                if (m == 60) {
                    m = 0;
                }

                // Update the text on the UI thread with leading zeros for minutes and seconds
                Timer.this.setText(String.format("%02d:%02d", m, s));

                // Continue posting the update if runTimer is true
                if (runTimer) {
                    updateTimer();
                }
            }
        }, 1000); // Schedule the update after 1 second
    }
}
