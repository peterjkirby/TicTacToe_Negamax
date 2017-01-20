package ttt;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 11/13/2016.
 */
public class TransTableTest {

    private TransTable transTable;
    private Zobrist zobrist;

    @org.junit.Before
    public void setUp() throws Exception {
        transTable = new TransTable();
        zobrist = new Zobrist()
                .setSize(3)
                .build();
    }

    @Test
    public void testIsometricEquality() {
        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(-1, new Move(2,2));

        transTable.set(b1, 1);

        int actual = transTable.get(b1);
        assertEquals(1, actual);

        List<Board> isometries = b1.generateIsometries();

        for (Board iso : isometries) {
            actual = transTable.get(iso);
            assertEquals(1, actual);
        }


        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(1,1));

        assertNull(transTable.get(b2));
    }

}