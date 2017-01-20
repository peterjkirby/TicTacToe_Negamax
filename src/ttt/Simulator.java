package ttt;

/**
 * Created by peter on 10/30/2016.
 */
public class Simulator {

    // TODO remove before turning in... will not be needed after development
    private static final int MAX_IT = 10000;

    public void start(Game game) {
        Player current = game.getCurrentPlayer();
        Board board = game.getBoard();

        if (game.getShowOutput())
            board.draw();
        int it = MAX_IT;
        while (!board.isGameOver(game) && it > 0) {
            current.play(board);

            if (game.getShowOutput())
                board.draw();

            current = game.advance();
            --it;
        }
    }



}
