package ttt;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by peter on 10/30/2016.
 */
public class NegamaxAI implements AI {


    private int pid;
    private boolean firstMove = true;

    private int intelligence;

    public NegamaxAI(int pid, int intelligence) {
        this.pid = pid;
        this.intelligence = intelligence;
    }

    @Override
    public void play(Board board) {
        Move move;
        move = bestMove(board);
        board.move(pid, move);
    }

    @Override
    public int getId() {
        return pid;
    }

    public void setup(Game game) {}

    private int negamax(int pid, Board board, int depth) {
        int v = board.getGameValue(pid);
        if (v != -2)
            return v; /* game is over */

        if (depth == 0) return -1;

        v = -1;

        int size = board.getSize();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.canMove(r, c)) {

                    Move move = new Move(r, c);
                    board.move(pid, move);
                    int v0 = -negamax(-pid, board, depth - 1);

                    if (v0 > v)
                        v = v0;
                    board.unmakeMove(move);
                }
            }
        }
        return v;
    }

    private Move bestMove(Board board) {
        Move best = new Move();

        int max = -1;

        Iterator<Move> it = board.iterator();

        while (it.hasNext()) {
            Move move = it.next();
            board.move(pid, move);

            int score = -negamax(-pid, board, intelligence);

            if (score >= max) {
                best = move;
                max = score;
            }

            board.unmakeMove(move);
        }



        return best;

    }


}
