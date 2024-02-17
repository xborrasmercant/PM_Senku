package com.example.senku;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;

import androidx.core.content.ContextCompat;

public class Board extends GridLayout {
    private Cell[][] boardMatrix;
    private Cell selectedCell;
    private int[][] borders = new int[][]{ {0, 0}, {0, 1}, {1, 0}, {1, 1}, {0, 5}, {0, 6}, {1, 5}, {1, 6}, {5, 0}, {5, 1}, {6, 0}, {6, 1}, {5, 5}, {5, 6}, {6, 5}, {6, 6}}; // Cords to avoid checking borders of matrix
    public enum Direction { // Movement directions
        UP, DOWN, RIGHT, LEFT
    }

    public enum Status { // Current status of the board
        GAMEOVER, WIN, PLAYABLE
    }

    // CONSTRUCTOR
    public Board(Context context) {
        super(context);
        boardMatrix = new Cell[7][7];
        initBoard();
    }

    // GAME STATUS Methods
    public void handleBoardStatus() {
        switch (this.getBoardStatus()) {
            case WIN: {
                // TODO: Handle win
                System.exit(0);
            }
            case GAMEOVER: {
                // TODO: Handle game over
                System.exit(0);
            }
            case PLAYABLE: {
                return;
            }
        }
    }
    public Status getBoardStatus() {
        int pegCount = 0;

        for (Cell[] row : boardMatrix) {
            for (Cell cell : row) {
                if (cell.getValue() > 0 ) {
                    if (canMove(cell)) {return Status.PLAYABLE;};
                    pegCount++;
                }
            }
        }

        if (pegCount == 1) {return Status.WIN;}

        return Status.GAMEOVER;
    }
    public boolean canMove(Cell cell) {
        if (insideBounds(cell.getRowPos()+1, cell.getColPos(), cell.getRowPos()+2, cell.getColPos())) { // Try down
            return true;
        } else if (insideBounds(cell.getRowPos(), cell.getColPos()+1, cell.getRowPos(), cell.getColPos()+2)) { // Try up
            return true;
        } else if (insideBounds(cell.getRowPos()-1, cell.getColPos(), cell.getRowPos()-2, cell.getColPos())) { // Try left
            return true;
        } else if (insideBounds(cell.getRowPos(), cell.getColPos()-1, cell.getRowPos(), cell.getColPos()-2)) { // Try right
            return true;
        }

        return false;
    }
    private boolean insideBounds(int middleCellRow, int middleCellCol, int targetCellRow, int targetCellCol) {
        try {
            return boardMatrix[middleCellRow][middleCellCol].getValue() == 1 || boardMatrix[targetCellRow][targetCellCol].getValue() == 2;
        } catch (IndexOutOfBoundsException e) {
            // Return false if trying to access out of bounds
            return false;
        }
    }

    // MOVEMENT Methods
    public void handleMovement(Cell selectedCell, Cell targetCell, Direction movementDirection) {
        int midCellRow, midCellCol;

        // Get mid cell positions
        switch (movementDirection) {
            case DOWN:
                midCellRow = selectedCell.getRowPos()+1;
                midCellCol = selectedCell.getColPos();
                break;
            case UP:
                midCellRow = selectedCell.getRowPos()-1;
                midCellCol = selectedCell.getColPos();
                break;
            case RIGHT:
                midCellRow = selectedCell.getRowPos();
                midCellCol = selectedCell.getColPos()+1;
                break;
            case LEFT:
                midCellRow = selectedCell.getRowPos();
                midCellCol = selectedCell.getColPos()-1;
                break;
            default:
                midCellRow = 0;
                midCellCol = 0;
                break;
        }

        // Handle movement
        if (targetCell.getValue() == 0 && boardMatrix[midCellRow][midCellCol].getValue() == 1) {
            applyMovement(selectedCell, targetCell, midCellRow, midCellCol);
        }
    }
    public void applyMovement(Cell selectedCell, Cell targetCell, int row, int col) {
        selectedCell.updateCell(0);
        boardMatrix[row][col].updateCell(0); // Empty cell between selected and target cells
        targetCell.updateCell(1);
    }
    public Direction getDirection(Cell selectedCell, Cell targetCell) {

        // Check the difference result of selected and target pegs.
        int rowDif = selectedCell.getRowPos() - targetCell.getRowPos(), colDif = selectedCell.getColPos() - targetCell.getColPos();


        if (rowDif > 2 || rowDif < -2 || colDif > 2 || colDif < -2) {
            return null;
        }

        if (colDif == 0) {
            switch (rowDif) {
                case -2:
                    return Direction.DOWN;
                case 2:
                    return Direction.UP;
                default:
                    return null;
            }
        }

        switch (colDif) {
            case -2:
                return Direction.RIGHT;
            case 2:
                return Direction.LEFT;
            default:
                return null;
        }
    }

    // INITIALIZATION Methods
    public void initBoard() {

        // Fill with pegs
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = new Cell(getContext(), col, row, 1);
                boardMatrix[row][col] = cell;

                cell.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            if (selectedCell != null) {
                                handleMovement(selectedCell, cell, getDirection(selectedCell, cell));
                                selectedCell.getCellSprite().clearColorFilter();
                                selectedCell = null;

                            } else {
                                selectedCell = cell;
                                cell.getCellSprite().setColorFilter(ContextCompat.getColor(getContext(), R.color.selected));
                            }
                        } catch (Exception e) {
                            // Handle
                        }

                    }
                });

                GridLayout.LayoutParams params = createGridLayoutParams(row, col);
                this.addView(boardMatrix[row][col], params); // Add to Layout
            }
        }

        // Define borders of boardMatrix
        for (int[] pos : borders) {
            boardMatrix[pos[0]][pos[1]].updateCell(-1);
        }

        // Set the empty position of the board (center)
        boardMatrix[3][3].updateCell(0);
    }
    public void initTestingBoard() {

        // Fill with empty cells
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                boardMatrix[row][col] = new Cell(getContext(), col, row, 0);
            }
        }

        // Define borders of boardMatrix
        for (int[] pos : borders) {
            boardMatrix[pos[0]][pos[1]].updateCell(-1);
        }

        // Set two pegs for testing the game over method
        boardMatrix[3][3].updateCell(1);
        boardMatrix[2][3].updateCell(1);
    }

    public GridLayout.LayoutParams createGridLayoutParams(int row, int col) {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(row);
        params.columnSpec = GridLayout.spec(col);

        return params;
    }

    // GETTERS and SETTERS
    public Cell[][] getBoardMatrix() {
        return boardMatrix;
    }
    public void setBoardMatrix(Cell[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
    }
    public int[][] getBorders() {
        return borders;
    }
    public void setBorders(int[][] borders) {
        this.borders = borders;
    }
}

