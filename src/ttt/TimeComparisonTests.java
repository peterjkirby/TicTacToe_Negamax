package ttt;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter on 11/13/2016.
 */
public class TimeComparisonTests {

    private static final int MAX_INTELLIGENCE = 10;
    private static final int MAX_SIZE = 4;

    public static void main(String[] args) throws IOException {
        int runs = 10;
        TimeComparisonTests tests = new TimeComparisonTests();
        tests.collectData(runs);
    }

    private void collectData(int runs) throws IOException {

        FileWriter writer = new FileWriter("results-size"+MAX_SIZE + "-int" + MAX_INTELLIGENCE+"-" + "4ABvABSYM"  +".csv", true);

        for (int intelligence = 4; intelligence <= MAX_INTELLIGENCE; ++intelligence) {

            for (int size = MAX_SIZE; size <= MAX_SIZE; ++size) {
                //double a = negamaxGame(runs, intelligence, size);
                double b = negamaxABGame(runs, intelligence, size);
                double c = negamaxABSymGame(runs, intelligence, size);
               // double d = negamaxSymGame(runs, intelligence, size);
               // writer.write("negamaxGame," +  a + "," + intelligence + "," + size + "\n");
                writer.write("negamaxABGame," + b+ "," + intelligence + "," + size + "\n");
                writer.write("negamaxABSymGame," + c  + "," + intelligence + "," + size + "\n");
               // writer.write("negamaxSymGame," + d + "," + intelligence + "," + size + "\n");
                writer.flush();
            }

        }
        writer.close();
    }

    private double negamaxGame(int runs, int intelligence, int size) {
        long start = System.nanoTime();
        for (int i = 0; i < runs; ++i) {
            Game game = new Game()
                    .setBoardSize(size)
                    .setPlayer1(new NegamaxAI(1, intelligence))
                    .setPlayer2(new NegamaxAI(-1, intelligence))
                    .showOutput(false)
                    .start();
        }
        long end = System.nanoTime();

        long duration = TimeUnit.NANOSECONDS.toMillis(end - start);
        return duration / (double) runs;
    }

    private double negamaxABGame(int runs, int intelligence, int size) {
        long start = System.nanoTime();
        for (int i = 0; i < runs; ++i) {
            Game game = new Game()
                    .setBoardSize(size)
                    .setPlayer1(new NegamaxABAI(1, intelligence))
                    .setPlayer2(new NegamaxABAI(-1, intelligence))
                    .showOutput(false)
                    .start();
        }
        long end = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(end - start);
        return duration / (double) runs;
    }

    private double negamaxABSymGame(int runs, int intelligence, int size) {
        long start = System.nanoTime();
        for (int i = 0; i < runs; ++i) {
            Game game = new Game()
                    .setBoardSize(size)
                    .useSymPreemptiveHashing(true)
                    .setPlayer1(new NegamaxABSymmtryAI(1, intelligence))
                    .setPlayer2(new NegamaxABSymmtryAI(-1, intelligence))
                    .showOutput(false)
                    .start();
        }
        long end = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(end - start);
        return duration / (double) runs;
    }

    private double negamaxSymGame(int runs, int intelligence, int size) {
        long start = System.nanoTime();
        for (int i = 0; i < runs; ++i) {
            Game game = new Game()
                    .setBoardSize(size)
                    .useSymPreemptiveHashing(true)
                    .setPlayer1(new NegamaxSymmtryAI(1, intelligence))
                    .setPlayer2(new NegamaxSymmtryAI(-1, intelligence))
                    .showOutput(false)
                    .start();
        }
        long end = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(end - start);
        return duration / (double) runs;
    }
}
