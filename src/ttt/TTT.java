package ttt;/*
 * Copyright © 2012 Bart Massey
 * [This program is licensed under the "MIT License"]
 * Please see the file COPYING in the source
 * distribution of this software for license terms.
 */

/* Perfect Tic-Tac-Toe player in Nickle */

import java.util.concurrent.TimeUnit;

public class TTT {

    static boolean timing_loop = true;


    public static void main(String[] args) {

        long start = System.nanoTime();

        //for (int i = 0; i < 10; ++i) {
            Game game = new Game()
                    .setBoardSize(3)
                    //.setPlayer1(new NegamaxAI(1))
                    //.setPlayer2(new NegamaxAI(-1))
                   // .setPlayer1(new NegamaxABAI(1, 4))
                    //.setPlayer2(new NegamaxABAI(-1, 4))
                    .setPlayer1(new NegamaxSymmtryAI(1, 9))
                    .setPlayer2(new NegamaxSymmtryAI(-1, 9))
                    .useSymPreemptiveHashing(true)
                    //.setPlayer1(new NegamaxABSymmtryAI(1, 4))
                    //.setPlayer2(new NegamaxABSymmtryAI(-1, 4))
                    .start();
        //}
        long end = System.nanoTime();


        long duration = end - start;
        //System.out.println("Runtime" + TimeUnit.NANOSECONDS.toSeconds(duration));




    }

}
