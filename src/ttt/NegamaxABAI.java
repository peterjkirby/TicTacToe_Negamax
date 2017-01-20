package ttt;

import java.util.Random;

/**
 * Created by peter on 10/30/2016.
 */
public class NegamaxABAI implements AI {

    private static final int MIN_VALUE = -1;
    private static final int MAX_VALUE = 1;

    private int pid;
    private boolean firstMove = false;
    private int intelligence;

    public NegamaxABAI(int pid, int intelligence) {
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

    private int negamax(int pid, int alpha, int beta, Board board, int depth) {

        int v =  board.getGameValue(pid);

        if (v != -2)
            return v;

        if (depth == 0) return -1;

        v = -1;
        int size = board.getSize();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.canMove(r, c)) {
                    Move m = new Move(r, c);
                    board.move(pid ,m);

                    int v0 = -negamax(-pid, -beta, -alpha, board, depth - 1);
                    if (v0 > v)
                        v = v0;

                    board.unmakeMove(m);
                    alpha = Math.max(alpha, v);
                    if (alpha >= beta) return alpha;

                }
            }
        }
        return alpha;
    }

    private Move bestMove(Board board) {
        Move best = new Move();

        int max = MIN_VALUE;
        for (int r = 0; r < board.getSize(); ++r) {
            for (int c = 0; c < board.getSize(); ++c) {

               // System.out.println("bestMove(r,c): " + r + " , " + c);
                Move m = new Move(r,c );
                if (board.canMove(r, c)) {
                    board.move(pid, m);

                    int score = -negamax(-pid, MIN_VALUE, MAX_VALUE, board, intelligence);

                    if (score >= max) {
                        best.r = r;
                        best.c = c;
                        max = score;

                    }

                    board.unmakeMove(m);
                }
            }
        }


        return best;

    }


}
