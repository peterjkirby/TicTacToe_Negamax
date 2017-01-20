package ttt;

import java.util.*;

/**
 * Created by peter on 10/30/2016.
 */
public class Board {

    private int[][] board;
    private long hash = 0;

    private boolean usePreemptiveSymHashing = false;
    private int size;
    private Zobrist zobrist;
    private List<Long> hashes;

    public Board(int size, Zobrist zobrist) {
        this.size = size;
        this.zobrist = zobrist;
        board = new int[size][size];
    }

    private Board(int[][] board, int size, long hash, Zobrist zobrist) {
        this.board = board;
        this.size = size;
        this.hash = hash;
        this.zobrist = zobrist;
    }

    public void move(int player, Move move) {
        if (board[move.r][move.c] != 0)
            throw new IllegalArgumentException("Invalid move. (" + move.r + ", " + move.c + ")");

        if (!usePreemptiveSymHashing) {
            long bitstr = zobrist.get(player, move.r, move.c);
            hash = hash ^ bitstr;
        } else {
            updateHashes(player, move);
        }


        board[move.r][move.c] = player;
    }

    private void updateHash(int idx, int player, int row, int col) {
        hashes.set(idx, hashes.get(idx) ^ zobrist.get(player, row, col));
    }

    private void updateHashes(int player, Move move) {
        // still update the main boards hash b/c unit tests require it
        hash = hash ^ zobrist.get(player, move.r, move.c);
        updateHash(0, player, move.r, move.c);

        // 90 degree rotation
        updateHash(1, player, move.c, size - 1 - move.r);

        // 180 degree rotation
        updateHash(2, player, size - 1 - move.r, size - 1 - move.c);

        // 270 degree rotation
        updateHash(3, player, size - 1 - move.c, move.r);

        // reflect
        Move reflect = new Move(size - 1 - move.r, move.c);

        // reflected
        updateHash(4, player, reflect.r, reflect.c);

        // reflected 90 degree rotation
        updateHash(5, player, reflect.c, size - 1 - reflect.r);

        // reflected 180 degree rotation
        updateHash(6, player, size - 1 - reflect.r, size - 1 - reflect.c);

        // reflected 270 degree rotation
        updateHash(7, player, size - 1 - reflect.c, reflect.r);
    }

    public int getGameValue(int pid) {
        return Gamevalue.gamevalue(pid, board, size);
    }

    public void unmakeMove(Move move) {
        if (board[move.r][move.c] == 0)
            throw new IllegalArgumentException("Cannot unmake move. No move has been made at these coordinates.");

        int player = board[move.r][move.c];
        if (!usePreemptiveSymHashing) {
            long bitstr = zobrist.get(player, move.r, move.c);
            hash = hash ^ bitstr;
        } else {
            updateHashes(player, move);
        }

        board[move.r][move.c] = 0;
    }

    public boolean canMove(int r, int c) {
        return board[r][c] == 0;
    }

    public boolean isGameOver(Game game) {
        Player current = game.getCurrentPlayer();
        return getGameValue(current.getId()) != -2 && getGameValue(-current.getId()) != -2;
    }

    public int getSize() { return size; }

    public Iterator<Move> iterator() {
        return new BoardIterator(this);
    }

    public void draw() {
        for (int i = 0; i < size; ++i) {
            String row = "";
            for (int j = 0; j < size; ++j) {
                row += board[i][j] + " ";
            }
            System.out.println(row);
        }
        System.out.println("    ");
    }


    public Board horizontalReflect() {
        int[][] newBoard = new int[size][size];
        long hash = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newBoard[size - j - 1][i] = board[j][i];
                if (newBoard[size - j - 1][i] != 0) {
                    long bitstr = zobrist.get(newBoard[size - j - 1][i], size - j - 1, i);
                    hash = hash ^ bitstr;
                }
            }
        }
        return new Board(newBoard, size, hash, zobrist);
    }

    public Board verticalReflect() {
        int[][] newBoard = new int[size][size];
        long hash = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newBoard[i][size - j - 1] = board[i][j];
                if (newBoard[i][size - j - 1] != 0) {
                    long bitstr = zobrist.get(newBoard[i][size - j - 1], i, size - j - 1);
                    hash = hash ^ bitstr;
                }
            }
        }
        return new Board(newBoard, size, hash, zobrist);
    }

    public Board rotate90() {
        int[][] newBoard = new int[size][size];
        long hash = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newBoard[j][size-1-i] = board[i][j];
                if (newBoard[j][size-1-i] != 0) {
                    long bitstr = zobrist.get(newBoard[j][size-1-i], j, size-1-i);
                    hash = hash ^ bitstr;
                }
            }
        }
        return new Board(newBoard, size, hash, zobrist);
    }

    public Board rotate270() {
        int[][] newBoard = new int[size][size];
        long hash = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                newBoard[i][j] = board[j][size - i - 1];
                if (newBoard[i][j] != 0) {
                    long bitstr = zobrist.get(newBoard[i][j], i, j);
                    hash = hash ^ bitstr;
                }
            }
        }

        return new Board(newBoard, size, hash, zobrist);
    }

    public Board rotate180() {
        int[][] newBoard = new int[size][size];
        long hash = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                newBoard[size - i - 1][size - j - 1] = board[i][j];
                if (newBoard[size - i - 1][size - j - 1] != 0) {
                    long bitstr = zobrist.get(newBoard[size - i - 1][size - j - 1], size - i - 1, size - j - 1);
                    hash = hash ^ bitstr;
                }
            }
        }

        return new Board(newBoard, size, hash, zobrist);
    }

    public List<Board> generateIsometries() {
        List<Board> boards = new ArrayList<>(6);
        boards.add(this);
        boards.add(rotate90());
        boards.add(rotate180());
        boards.add(rotate270());

        Board f = horizontalReflect();
        boards.add(f);
        boards.add(f.rotate90());
        boards.add(f.rotate180());
        boards.add(f.rotate270());
        return boards;
    }

    public List<Long> getHashes() {
        return hashes;
    }

    /**
     * whether or not this board is managing the hashes of all 8 isometric equivalents
     * @return
     */
    public boolean getUsePreemptiveSymHashing() {
        return usePreemptiveSymHashing;
    }

    public void setUsePreemptiveSymHashing(boolean usePreemptiveSymHashing) {
        this.usePreemptiveSymHashing = usePreemptiveSymHashing;
        if (usePreemptiveSymHashing) {
            hashes = new ArrayList<>(8);
            for (int i = 0; i < 8; ++i)
                hashes.add(0L);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return board1.hash == hash;
    }

    public long getHash() {
        return hash;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(hash);
    }
}
