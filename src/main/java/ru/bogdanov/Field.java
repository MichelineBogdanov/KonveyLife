package ru.bogdanov;

import java.util.Random;

public class Field {

    private final Cell[][] field;
    private final int xSize;
    private final int ySize;

    public Field(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.field = new Cell[xSize][ySize];
        initField();
    }

    private void initField() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                int stateRandom = new Random().nextInt(5);
                if (stateRandom > 3) {
                    field[i][j] = new Cell(i, j, CellState.LIFE);
                } else {
                    field[i][j] = new Cell(i, j, CellState.DEAD);
                }
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x > xSize - 1 || y < 0 || y > ySize) {
            return null;
        }
        return field[x][y];
    }

    public void setCell(Cell cell) {
        field[cell.x()][cell.y()] = cell;
    }

    public int getFieldXLength() {
        return xSize;
    }

    public int getFieldYLength() {
        return ySize;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                stringBuilder.append(cell.state().getSymbol());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
