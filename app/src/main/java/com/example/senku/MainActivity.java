package com.example.senku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout mainConstraintLayout;
    ConstraintLayout.LayoutParams mainCLayoutParams;
    int displayWidth, displayHeight;
    Board board;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainConstraintLayout = findViewById(R.id.mainConstraintLayout);

        addComponentsToLayout();
        configureConstraints();
    }


    private void addComponentsToLayout() {

        board = new Board (getBaseContext());
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

        cs.clone(mainConstraintLayout); // Clone the constraints of mainConstraintLayout into ConstraintSet

        cs.connect(boardID, ConstraintSet.START, parentID, ConstraintSet.START, spacing);
        cs.connect(boardID, ConstraintSet.END, parentID, ConstraintSet.END, spacing);
        cs.connect(boardID, ConstraintSet.TOP, parentID, ConstraintSet.TOP, spacing);
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