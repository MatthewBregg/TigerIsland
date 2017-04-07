package tigerislandserver.gameplay;

import tigerisland.score.ScoreManager;
import tigerisland.tile.Tile;
import tigerislandserver.gameplay.identifiers.MatchID;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;


public class Match extends Thread {
    private ArrayList<Tile> gameTiles;
    private GameThread game1, game2;
    private ArrayList<TournamentPlayer> players;
    private long matchID;

    public Match(ArrayList<TournamentPlayer> playerList, ArrayList<Tile> tiles, TournamentScoreboard scoreboard){
        players = playerList;
        gameTiles = tiles;
        matchID = MatchID.getID();
        game1 = new GameThread(players, gameTiles, 'A', scoreboard);
        game2 = new GameThread(players, gameTiles, 'B', scoreboard);
    }

    public void startGames(){
        game1.start();
        game2.start();
    }

    public void run(){
        // TODO: call Match protocol
        // TODO: start games, then get game results when ended
    }

    public long getMatchID(){
        return matchID;
    }
}
