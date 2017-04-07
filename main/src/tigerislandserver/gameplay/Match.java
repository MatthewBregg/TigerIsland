package tigerislandserver.gameplay;

import tigerisland.score.ScoreManager;
import tigerisland.tile.Tile;
import tigerislandserver.adapter.OutputAdapter;
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
        game1 = new GameThread(players.get(0), players.get(1), gameTiles, 'A', scoreboard);
        game2 = new GameThread(players.get(1), players.get(0), gameTiles, 'B', scoreboard);
    }

    public void startGames(){
        game1.start();
        game2.start();
    }

    public void run(){
        OutputAdapter.sendStartMatchMessage(players.get(0), players.get(1));
        startGames();
    }

    public long getMatchID(){
        return matchID;
    }
}
