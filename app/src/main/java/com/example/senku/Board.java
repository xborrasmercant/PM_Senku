package com.example.senku;

import android.content.Context;
import android.widget.GridLayout;

public class Board extends GridLayout {
    private Cell[][] boardMatrix;
    private int[][] borders = new int[][]{ {0, 0}, {0, 1}, {1, 0}, {1, 1}, {0, 5}, {0, 6}, {1, 5}, {1, 6}, {5, 0}, {5, 1}, {6, 0}, {6, 1}, {5, 5}, {5, 6}, {6, 5}, {6, 6}}; // Coords to avoid checking borders of matrix
    public enum Direction { // Movement directions
        UP, DOWN, RIGHT, LEFT;
    };

    public enum Status { // Current status of the board
        GAMEOVER, WIN, PLAYABLE;
    };

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
        switch (movementDirection) {
            case DOWN: {applyMovement(selectedCell, targetCell, selectedCell.getRowPos()+1, selectedCell.getColPos())};
            case UP: {applyMovement(selectedCell, targetCell, selectedCell.getRowPos()-1, selectedCell.getColPos())};
            case RIGHT: {applyMovement(selectedCell, targetCell, selectedCell.getRowPos(), selectedCell.getColPos()+1)};
            case LEFT: {applyMovement(selectedCell, targetCell, selectedCell.getRowPos(), selectedCell.getColPos()-1)};
        }
    }
    public void applyMovement(Cell selectedCell, Cell targetCell, int row, int col) {
        selectedCell.setValue(0);
        boardMatrix[row][col].setValue(0); // Empty cell between selected and target cells
        targetCell.setValue(1);
    }
    public Direction getDirection(Cell selectedCell, Cell targetCell) {

        // Check the difference result of selected and target pegs.
        int rowDif = selectedCell.getRowPos() - targetCell.getRowPos(), colDif = selectedCell.getColPos() - targetCell.getColPos();


        if (rowDif > 2 || rowDif < -2 || colDif > 2 || colDif < -2) {
            return null;
        }

        if (colDif == 0) {
            return switch (rowDif) {
                case -2: Direction.DOWN;
                case 2: {Direction.UP};
                default: {null};
            };
        }

        return switch (colDif) {
            case -2 -> Direction.RIGHT;
            case 2 -> Direction.LEFT;
            default -> null;
        };
    }

    // INITIALIZATION Methods
    public void initBoard() {
        // Fill with pegs
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                boardMatrix[row][col] = new Cell(col, row, 1);
            }
        }

        // Define borders of boardMatrix
        for (int[] pos : borders) {
            boardMatrix[pos[0]][pos[1]].setValue(-1);
        }

        // Set the empty position of the board (center)
        boardMatrix[3][3].setValue(0);
    }
    public void initTestingBoard() {

        // Fill with empty cells
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                boardMatrix[row][col] = new Cell(col, row, 0);
            }
        }

        // Define borders of boardMatrix
        for (int[] pos : borders) {
            boardMatrix[pos[0]][pos[1]].setValue(-1);
        }

        // Set two pegs for testing the game over method
        boardMatrix[3][3].setValue(1);
        boardMatrix[2][3].setValue(1);
    }

    //TERMINAL Methods
    public void cleanTerminal(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }
    public void printBoard() {
        cleanTerminal();

        for (Cell[] row : boardMatrix) {
            for (Cell peg : row) {
                if (peg.getValue() == -1) {
                    System.out.print("  ");
                } else {
                    System.out.print(peg.getValue() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();

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
}
