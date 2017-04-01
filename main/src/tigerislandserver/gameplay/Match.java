package tigerislandserver.gameplay;

import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class Match extends Thread {
    private ArrayList<Tile> gameTiles;
    private GameThread game1, game2;
    private ArrayList<TournamentPlayer> players;

    public Match(ArrayList<TournamentPlayer> playerList, ArrayList<Tile> tiles){
        players = playerList;
        gameTiles = tiles;
        game1 = new GameThread(players, gameTiles, nextGameID());
        game2 = new GameThread(players, gameTiles, nextGameID());
    }

    private int nextGameID(){
        int newID = 0;

        // TODO: code for finding game IDs

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
