package tigerislandserver.gameplay;

import tigerisland.player.Player;

public class Match extends Thread {
    private long seed;
    private Game game1, game2;
    private Player player1, player2;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        seed = generateSeed();
        game1 = new Game(player1, player2, seed, nextGameID());
        game2 = new Game(player2, player1, seed, nextGameID());
    }

    private long generateSeed(){
        long randomSeed;
        randomSeed = (long) Math.floor(Math.random());
        return randomSeed;
    }

    private int nextGameID(){
        int newID = 0;

        // code for finding game IDs

        return newID;
    }

    public void startGames(){
        game1.start();
        game2.start();
    }

    public void run(){
        // TODO
    }
}
