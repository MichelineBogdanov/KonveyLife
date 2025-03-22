package ru.bogdanov;

public record Cell(int x, int y, CellState state) {

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                '}';
    }
}
