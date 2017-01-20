package ttt;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by peter on 11/13/2016.
 */
public class BoardTest {

    private Zobrist zobrist;

    @Before
    public void setUp() throws Exception {
        zobrist = new Zobrist()
                .setSize(3)
                .build();
    }

    @Test
    public void testPreemptiveHashEquality() {
        Board b1 = new Board(3, zobrist);
        b1.setUsePreemptiveSymHashing(true);

        b1.move(1, new Move(0,0));
        b1.move(-1, new Move(2,2));

        // order: 0, 90, 180, 270, R, R90, R180, R279
        List<Long> hashes = b1.getHashes();

        assertTrue(hashes.get(0).equals(b1.getHash()));
        assertTrue(hashes.get(1).equals(b1.rotate90().getHash()));
        assertTrue(hashes.get(2).equals(b1.rotate180().getHash()));
        assertTrue(hashes.get(3).equals(b1.rotate270().getHash()));
        assertTrue(hashes.get(4).equals(b1.horizontalReflect().getHash()));
        assertTrue(hashes.get(5).equals(b1.horizontalReflect().rotate90().getHash()));
        assertTrue(hashes.get(6).equals(b1.horizontalReflect().rotate180().getHash()));
        assertTrue(hashes.get(7).equals(b1.horizontalReflect().rotate270().getHash()));
    }

    @Test
    public void testEquality1() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(-1, new Move(2,2));

        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(0,0));
        b2.move(-1, new Move(2,2));

        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
    }

    @Test
    public void testEquality2() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(-1, new Move(2,2));

        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(1,1));
        b2.move(-1, new Move(2,2));

        assertFalse(b1.equals(b2));
        assertFalse(b2.equals(b1));
    }

    @Test
    public void testEquality3() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));

        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(0,0));
        b2.move(-1, new Move(2,2));

        assertFalse(b1.equals(b2));

        b1.move(-1, new Move(2,2));
        assertTrue(b1.equals(b2));
    }

    @Test
    public void testRotation() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(1, new Move(0,1));
        b1.move(-1, new Move(1,1));
        b1.move(-1, new Move(0,2));


        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(0,2));
        b2.move(1, new Move(1,2));
        b2.move(-1, new Move(2,2));
        b2.move(-1, new Move(1,1));


        assertFalse(b1.equals(b2));
        assertTrue(b1.rotate90().equals(b2));
        assertTrue(b2.rotate270().equals(b1));
        assertTrue(b2.rotate90().equals(b1.rotate180()));
    }

    @Test
    public void testVerticalSymmetry() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(1, new Move(0,1));
        b1.move(-1, new Move(1,1));
        b1.move(-1, new Move(0,2));


        Board b2 = new Board(3, zobrist);
        b2.move(-1, new Move(0,0));
        b2.move(1, new Move(0, 1));
        b2.move(-1, new Move(1,1));
        b2.move(1, new Move(0, 2));


        assertFalse(b1.equals(b2));

        assertTrue(b1.verticalReflect().equals(b2));
        assertTrue(b2.verticalReflect().equals(b1));

    }

    @Test
    public void testHorizontalSymmetry() {

        Board b1 = new Board(3, zobrist);
        b1.move(1, new Move(0,0));
        b1.move(1, new Move(0,1));
        b1.move(-1, new Move(1,1));
        b1.move(-1, new Move(0,2));

        Board b2 = new Board(3, zobrist);
        b2.move(1, new Move(2,0));
        b2.move(1, new Move(2, 1));
        b2.move(-1, new Move(1,1));
        b2.move(-1, new Move(2, 2));

        assertFalse(b1.equals(b2));

        assertTrue(b1.horizontalReflect().equals(b2));
        assertTrue(b2.horizontalReflect().equals(b1));

    }
}