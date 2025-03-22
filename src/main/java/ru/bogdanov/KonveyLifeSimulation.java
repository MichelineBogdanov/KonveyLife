package ru.bogdanov;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KonveyLifeSimulation {

    public static void main(String[] args) {
        int x;
        int y;
        int steps;
        if (args.length == 3) {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            steps = Integer.parseInt(args[2]);
        } else {
            x = 10;
            y = 10;
            steps = 100;
        }
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.putString("Step");
            Field field = new Field(x, y);
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int step = 0; step < steps; step++) {
                terminal.clearScreen();
                terminal.setCursorPosition(0, 0);
                terminal.putString("Step " + step);
                String[] fieldLines = field.toString().split("\n");
                for (int i = 0; i < fieldLines.length; i++) {
                    terminal.setCursorPosition(0, i + 1);
                    terminal.putString(fieldLines[i]);
                }
                terminal.flush();
                field = updateField(field, executor);
                Thread.sleep(250);
            }
            executor.shutdown();
            terminal.exitPrivateMode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field updateField(Field field, ExecutorService executor) {
        Field nextGen = new Field(field.getFieldXLength(), field.getFieldYLength());
        List<Future<Cell>> futures = new ArrayList<>();
        for (int i = 0; i < field.getFieldXLength(); i++) {
            for (int j = 0; j < field.getFieldYLength(); j++) {
                CellWorker task = new CellWorker(field.getCell(i, j), field);
                futures.add(executor.submit(task));
            }
        }
        for (Future<Cell> future : futures) {
            try {
                Cell cell = future.get();
                nextGen.setCell(cell);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return nextGen;
    }

}