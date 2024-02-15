package com.example.senku;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Cell extends FrameLayout {
    private int colPos, rowPos, value;
    private ImageView cellSprite;

    public Cell (Context context, int colPos, int rowPos, int value) {
        super(context);
        this.colPos = colPos;
        this.rowPos = rowPos;
        this.value = value;
        this.cellSprite = new ImageView(getContext());
    }

    // METHODS
    public void updateCell(int value) {
        setValue(value);
        setCellSprite();
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
    public void setCellSprite() {
        switch (this.getValue()) {
            case 1: {this.cellSprite.setImageResource(R.drawable.sprite_cellNull);}
            default: {this.cellSprite.setImageResource(R.drawable.sprite_cellNull);}
        }

    }
}
