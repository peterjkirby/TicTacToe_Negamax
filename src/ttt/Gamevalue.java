package ttt;/*
 * Copyright © 2012 Bart Massey
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

/* Perfect Tic-Tac-Toe player in Java */

/*
  Returns the value of the game to the
  side on move if the game is over, or
  -2 if the game is still in progress.
*/
class Gamevalue {



    static int gamevalue(int onmove, int[][] board, int size) {
        int v;
    /* first scan for wins */
        int side = -1;
        for (int s = 0; s < 2; s++) {
            side = -side;
            v = side * onmove;
            /* scan for diagonal */
            int n = 0;
            for (int d = 0; d < size; d++)
                if (board[d][d] == side)
                    n = n + 1;
            if (n == size)
                return v;
            /* scan for opposite diagonal */
            n = 0;
            for (int d = 0; d < size; d++)
                if (board[d][size - 1 - d] == side)
                    n = n + 1;
            if (n == size)
                return v;
            /* scan for rows */
            for (int r = 0; r < size; r++) {
                n = 0;
                for (int c = 0; c < size; c++)
                    if (board[r][c] == side)
                        n = n + 1;
                if (n == size)
                    return v;
            }
          /* scan for columns */
            for (int c = 0; c < size; c++) {
                n = 0;
                for (int r = 0; r < size; r++)
                    if (board[r][c] == side)
                        n = n + 1;
                if (n == size)
                    return v;
            }
        }
    /* scan for blanks */
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (board[r][c] == 0)
          /* game not over */
                    return -2;
    /* game is a draw */
        return 0;
    }

}
