package ru.bogdanov;

public enum CellState {

    LIFE('@'),
    DEAD('*');

    private final char symbol;

    CellState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

}
