package com.example.senku;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

public class GameActivity extends AppCompatActivity implements MoveListener {
    private ConstraintLayout gameConstraintLayout;
    private Board board;
    private Timer timer;
    private ScoreBox scoreBox;
    private int displayWidth, displayHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameConstraintLayout = findViewById(R.id.gameConstraintLayout);

        saveDisplaySize();
        addComponentsToLayout();
        styleActivity();
    }

    private void addComponentsToLayout() {
        // Add Timer
        timer = new Timer(this, 50, ContextCompat.getColor(this, R.color.defaultText), ContextCompat.getColor(this, R.color.defaultBackgroundText));
        timer.setId(View.generateViewId());
        gameConstraintLayout.addView(timer);
        timer.startTimer();

        // Add Scorebox
        scoreBox = new ScoreBox(this, 50, ContextCompat.getColor(this, R.color.defaultText), ContextCompat.getColor(this, R.color.defaultBackgroundText), 0);
        scoreBox.setId(View.generateViewId());
        gameConstraintLayout.addView(scoreBox);
        extendViewWidth(scoreBox);

        // Add Board
        board = new Board(this);
        board.setId(View.generateViewId());
        gameConstraintLayout.addView(board);
        board.setMoveListener(this);
        board.initBoard();

        configureConstraints();
    }

    private void configureConstraints() {
        // ConstraintSet Configuration
        ConstraintSet cs = new ConstraintSet();
        int spacing = 32;
        int parentID = ConstraintSet.PARENT_ID;
        int boardID = board.getId();
        int timerID = timer.getId();
        int scoreBoxID = scoreBox.getId();

        cs.clone(gameConstraintLayout);

        // TIMER constraints
        cs.connect(timerID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(timerID, ConstraintSet.END, scoreBoxID, ConstraintSet.START, spacing);
        cs.connect(timerID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);

        // SCOREBOX constraints
        cs.connect(scoreBoxID, ConstraintSet.START, timerID, ConstraintSet.END, spacing);
        cs.connect(scoreBoxID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(scoreBoxID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);

        // BOARD constraints
        cs.connect(boardID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(boardID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(boardID, ConstraintSet.TOP, timerID, ConstraintSet.BOTTOM, spacing);
        cs.connect(boardID, ConstraintSet.TOP, scoreBoxID, ConstraintSet.BOTTOM, spacing);
        cs.connect(boardID, ConstraintSet.BOTTOM, parentID, ConstraintSet.BOTTOM, spacing);

        cs.applyTo(gameConstraintLayout);
    }

    private void extendViewWidth(View view) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
    }

    public void saveDisplaySize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
    }

    private void styleActivity() {
        gameConstraintLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.mainBackground));
    }

    @Override
    public void onMoveMade() {
        scoreBox.setScoreValue(25); // This is just an example
    }
}
