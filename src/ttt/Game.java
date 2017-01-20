package ttt;

import java.util.Random;

/**
 * Created by peter on 10/30/2016.
 */
public class Game {

    private Player player1;
    private Player player2;
    private int size;
    private int current;
    private Board board;

    private Simulator simulator;
    private static Game instance;
    private Zobrist zobrist;

    private boolean useSymPreemptiveHashing = false;
    private boolean showOutput = true;
    public Game() {
        instance = this;
    }

    public static Game getInstance() {
        return instance;
    }

    public Game start() {
        zobrist = new Zobrist()
                .setSize(size)
                .build();

        player1.setup(this);
        player2.setup(this);
        current = 1;
        board = new Board(size, zobrist);
        board.setUsePreemptiveSymHashing(useSymPreemptiveHashing);
        simulator = new Simulator();
        simulator.start(this);
        return this;
    }

    public Player advance() {
        if (current == 1)
            current = 2;
        else
            current = 1;

        return getCurrentPlayer();
    }

    public Player getCurrentPlayer() {
        switch (current){
            case 1:
                return player1;
            case 2:
                return player2;
            default:
                throw new IllegalStateException("Unrecognized player number");
        }
    }

    public Game showOutput(boolean showOutput) {
        this.showOutput = showOutput;
        return this;
    }

    public Game setPlayer1(Player player) {
        this.player1 = player;
        return this;
    }

    public Game setPlayer2(Player player2) {
        this.player2 = player2;
        return this;
    }

    public Game setBoardSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * Only use when transposition tables are being used.
     * @param useSymPreemptiveHashing
     * @return
     */
    public Game useSymPreemptiveHashing(boolean useSymPreemptiveHashing) {
        this.useSymPreemptiveHashing = useSymPreemptiveHashing;
        return this;
    }

    public boolean getShowOutput() { return showOutput; }

    public int getBoardSize() {
        return size;
    }

    public Zobrist getZobrist() {
        return zobrist;
    }

    public Player getPlayer1() {
        return player1;
    }


    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

}
