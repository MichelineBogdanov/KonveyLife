package ru.bogdanov;

import java.util.concurrent.Callable;

public class CellWorker implements Callable<Cell> {

    private final Cell cell;
    private final Field field;

    public CellWorker(Cell cell, Field field) {
        this.cell = cell;
        this.field = field;
    }

    @Override
    public Cell call() {
        CellState nextState = calculateNextState();
        return new Cell(cell.x(), cell.y(), nextState);
    }

    private CellState calculateNextState() {
        int liveNeighbors = countLiveNeighbors();
        if (cell.state() == CellState.LIFE) {
            return (liveNeighbors == 2 || liveNeighbors == 3) ? CellState.LIFE : CellState.DEAD;
        } else {
            return (liveNeighbors == 3) ? CellState.LIFE : CellState.DEAD;
        }
    }

    private int countLiveNeighbors() {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int nx = cell.x() + i;
                int ny = cell.y() + j;
                if (nx >= 0 && nx < field.getFieldXLength() && ny >= 0 && ny < field.getFieldYLength()) {
                    if (field.getCell(nx, ny).state() == CellState.LIFE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}
