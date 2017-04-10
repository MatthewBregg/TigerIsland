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

    public Match(ArrayList<TournamentPlayer> playerList, ArrayList<Tile> tiles, TournamentScoreboard scoreboard, int roundNumber){
        players = playerList;
        gameTiles = tiles;
        matchID = MatchID.getID();
        game1 = new GameThread(players.get(0), players.get(1), gameTiles, 'A', roundNumber, scoreboard);
        game2 = new GameThread(players.get(1), players.get(0), gameTiles, 'B', roundNumber, scoreboard);
    }

    public void startGames(){
        game1.start();
        game2.start();

        while(true)
        {
            if(game1.isAlive() && game2.isAlive())
            {
                break;
            }
            else
            {
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run(){
        OutputAdapter.sendStartMatchMessage(players.get(0), players.get(1));
        startGames();
    }

    public long getMatchID(){
        return matchID;
    }
}
