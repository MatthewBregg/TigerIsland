package tigerislandserver.gameplay;

import tigerisland.player.Player;
import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentClient;

import java.util.ArrayList;

public class Match extends Thread {
    private ArrayList<Tile> gameTiles;
    private GameThread game1, game2;
    private ArrayList<TournamentClient> players;

    public Match(ArrayList<TournamentClient> playerList, ArrayList<Tile> tiles){
        players = playerList;
        gameTiles = tiles;
        game1 = new GameThread(player1, player2, gameTiles, nextGameID());
        game2 = new GameThread(player2, player1, gameTiles, nextGameID());
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
