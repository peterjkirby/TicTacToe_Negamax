package ttt;

import java.util.Random;

/**
 * Created by peter on 11/13/2016.
 */
public class Zobrist {

    private static final int PLAYERS = 2;

    private long[][] keys;
    private int size;

    public Zobrist() {}

    public Zobrist setSize(int size) {
        this.size = size;
        return this;
    }

    public Zobrist build() {
        generateZobrist();
        return this;
    }

    /**
     * Returns a bitfield representing the unique player / move combination
     */
    public long get(int pid, int r, int c) {
        int piece = (pid == -1) ? 0 : 1;
        return keys[(r * size) + c][piece];
    }

    private void generateZobrist() {
        int positions = size * size;
        keys = new long[positions][PLAYERS];

        Random random = new Random();
        for (int i = 0; i < positions; ++i) {
            keys[i][0] = random.nextLong();
            keys[i][1] = random.nextLong();
        }
    }
}
