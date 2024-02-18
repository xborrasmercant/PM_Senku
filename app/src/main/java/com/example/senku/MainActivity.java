package com.example.senku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mainConstraintLayout;
    private ConstraintLayout.LayoutParams mainCLayoutParams;
    private int displayWidth, displayHeight;
    private Board board;
    private Timer timer;
    private ScoreBox scoreBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainConstraintLayout = findViewById(R.id.mainConstraintLayout);

        addComponentsToLayout();
        configureConstraints();
    }


    private void addComponentsToLayout() {
        // Add Timer
        timer = new Timer(this, 50, ContextCompat.getColor(this, R.color.defaultText), ContextCompat.getColor(this, R.color.defaultBackgroundText));
        timer.setId(View.generateViewId());
        mainConstraintLayout.addView(timer);
        timer.startTimer();

        // Add Scorebox
        scoreBox = new ScoreBox(this, 50, ContextCompat.getColor(this, R.color.defaultText), ContextCompat.getColor(this, R.color.defaultBackgroundText), 0);
        scoreBox.setId(View.generateViewId());
        mainConstraintLayout.addView(scoreBox);
        
        // Add Board
        board = new Board (this);
        board.setId(View.generateViewId());
        mainConstraintLayout.addView(board);
        board.initBoard();
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;
        int parentID = ConstraintSet.PARENT_ID;
        int boardID = board.getId();
        int timerID = timer.getId();
        int scoreBoxID = scoreBox.getId();

        cs.clone(mainConstraintLayout); // Clone the constraints of mainConstraintLayout into ConstraintSet

        // TIMER constraints
        cs.connect(timerID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(timerID, ConstraintSet.END, scoreBoxID, ConstraintSet.START, spacing);
        cs.connect(timerID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);
        cs.connect(timerID, ConstraintSet.BOTTOM, boardID, ConstraintSet.TOP, spacing);

        // SCOREBOX constraints
        cs.connect(scoreBoxID, ConstraintSet.START, timerID, ConstraintSet.END, spacing);
        cs.connect(scoreBoxID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(scoreBoxID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);
        cs.connect(scoreBoxID, ConstraintSet.BOTTOM, boardID, ConstraintSet.TOP, spacing);

        // BOARD constraints
        cs.connect(boardID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(boardID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        //cs.connect(boardID, ConstraintSet.TOP, timerID, ConstraintSet.BOTTOM, spacing);
        cs.connect(boardID, ConstraintSet.BOTTOM, parentID, ConstraintSet.BOTTOM, spacing);

        cs.applyTo(mainConstraintLayout);
    }

    public void saveDisplaySize(){
        // Getting display width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
    }
}