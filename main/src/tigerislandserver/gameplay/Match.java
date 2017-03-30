package tigerislandserver.gameplay;

import tigerisland.player.Player;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class Match extends Thread {
    private ArrayList<Tile> gameTiles;
    private Game game1, game2;
    private Player player1, player2;

    public Match(Player player1, Player player2, ArrayList<Tile> tiles){
        this.player1 = player1;
        this.player2 = player2;
        gameTiles = tiles;
        game1 = new Game(player1, player2, gameTiles, nextGameID());
        game2 = new Game(player2, player1, gameTiles, nextGameID());
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
