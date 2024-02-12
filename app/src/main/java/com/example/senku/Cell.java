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
    }

    // METHODS

    public void setCellSprite() {
        switch (value) {
            case -1: {cellSprite = new ImageView(getContext());}
            case 0: {cellSprite = new ImageView(getContext());}
            case 1: {cellSprite = new ImageView(getContext());}
        }
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
}
