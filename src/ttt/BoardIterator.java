package ttt;

import java.util.Iterator;

/**
 * Created by peter on 11/12/2016.
 */
public class BoardIterator implements Iterator<Move> {

    private Board board;
    private int r;
    private int c;
    private int size;
    private boolean done = false;

    public BoardIterator(Board board) {
        this.board = board;
        r = 0;
        c = 0;
        size = board.getSize();
    }

    @Override
    public boolean hasNext() {
        if (done) return false;

        if (r < size && c < size) {
            Move next = findNext();
            if (next == null)
                done = true;

            return !done;
        }

        return false;
    }

    @Override
    public Move next() {
        Move next = findNext();
        if (next == null) return null;
        r = next.r;
        c = next.c;

        if (c == size - 1) {
            c = 0;
            ++r;
        } else {
            ++c;
        }

        return next;
    }

    private Move findNext() {

        for (int i = r; i < size; ++i) {
            int j;
            if (i == r)
                j = c;
            else
                j = 0;

            for (;j < size; ++j) {
                if (board.canMove(i, j))
                    return new Move(i, j);
            }
        }

        return null;
    }
}
