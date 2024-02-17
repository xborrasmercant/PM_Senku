package com.example.senku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

public class Cell extends FrameLayout {
    private int colPos, rowPos, value, displayWidth, displayHeight;
    private ImageView cellSprite;


    public Cell (Context context, int colPos, int rowPos, int value) {
        super(context);
        setDisplaySizes();

        this.colPos = colPos;
        this.rowPos = rowPos;
        this.cellSprite = new ImageView(context);
        updateCell(value);
        addComponentsToLayout();
    }

    // METHODS
    public void updateCell(int value) {
        setValue(value);
        updateCellStyle();
    }

    public void addComponentsToLayout() {
        this.cellSprite.setLayoutParams(createFrameLayoutParams());
        this.addView(cellSprite);
    }

    public FrameLayout.LayoutParams createFrameLayoutParams() {
        int cellMargin = displayWidth*2/100; // Cell margin amount equals to the 3% of the displayWidth
        int cellSize = (displayWidth - cellMargin * 14) / 7; // Cell size equals to the displayWidth minus Cell Margin * 14 (because of left and right margins) divided by the amount of pegs (7)


        FrameLayout.LayoutParams params = new LayoutParams(cellSize, cellSize);
        params.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);
        return params;
    }

    public void setDisplaySizes() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        displayWidth = displayMetrics.widthPixels;
        displayHeight = displayMetrics.heightPixels;
    }

    // GETTERS and SETTERS
    public int getColPos() {
        return colPos;
    }
    public void setColPos(int colPos) {
        this.colPos = colPos;
    }
    public int getRowPos() {
        return rowPos;
    }
    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public ImageView getCellSprite() {
        return cellSprite;
    }

    public void updateCellStyle() {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorCellEmpty));


        if (this.getValue() == 1) {
            this.cellSprite.setImageResource(R.drawable.sprite_cellpeg);

        } else if (this.getValue() == 0) {
            this.cellSprite.setImageResource(R.drawable.sprite_cellnull);
        } else {
            this.cellSprite.setVisibility(View.INVISIBLE);
            this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.alpha));

        }
    }
}
