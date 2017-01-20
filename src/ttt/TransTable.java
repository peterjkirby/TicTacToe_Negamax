package ttt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by peter on 11/12/2016.
 */
public class TransTable {

    private HashMap<Long, Integer> ttable = new HashMap<>();
    private HashMap<Long, List<Board>> isometriesTable = new HashMap<>();


    public Integer get(Board board) {

        /*List<Board> isometries = getIsometries(board);

        for (Board b : isometries) {
            long hash = b.getHash();
            if (ttable.containsKey(hash))
                return ttable.get(hash);
        }*/

        List<Long> hashes = board.getHashes();
        for (Long h : hashes) {
            if (ttable.containsKey(h))
                return ttable.get(h);
        }


        /*if (ttable.containsKey(board.getHash()))
            return ttable.get(board.getHash());*/

        return null;
    }

    public void set(Board board, int value) {
        /*List<Board> isometries = getIsometries(board);
        for (Board b : isometries) {
            ttable.put(b.getHash(), value);
        }*/

        List<Long> hashes = board.getHashes();

        for (Long h : hashes)
            ttable.put(h, value);

        //ttable.put(board.getHash(), value);
    }

    private List<Board> getIsometries(Board board) {
        List<Board> isometries = isometriesTable.get(board.getHash());

        if (isometries == null) {
            isometries = board.generateIsometries();
            for (Board b : isometries) {
                isometriesTable.put(b.getHash(), isometries);
            }
        }

        return isometries;
    }

}
