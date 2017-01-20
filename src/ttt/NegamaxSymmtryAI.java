package ttt;

/**
 * Created by peter on 10/30/2016.
 */
public class NegamaxSymmtryAI implements AI {

    private int pid;
    private TransTable transTable = new TransTable();
    private int intelligence;

    public NegamaxSymmtryAI(int pid, int intelligence) {
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

                    Integer v0 = transTable.get(board);

                    if (v0 == null) {
                        v0 = -negamax(-pid, board, depth - 1);
                        transTable.set(board, v0);
                    }

                    if (v0 > v)
                        v = v0;

                    board.unmakeMove(m);

                }
            }
        }
        return v;
    }

    private Move bestMove(Board board) {
        Move best = new Move();

        int max = -1;
        int size = board.getSize();
        for (int r = 0; r < size; ++r) {
            for (int c = 0; c < size; ++c) {

                Move m = new Move(r,c );
                if (board.canMove(r, c)) {
                    board.move(pid, m);

                    int score = -negamax(-pid, board, intelligence);

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
