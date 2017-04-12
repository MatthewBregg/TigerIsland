package tigerislandserver.mocks;


import tigerisland.datalogger.DataLogger;
import tigerisland.game.GameManager;
import tigerisland.tile.Tile;
import tigerislandserver.gameplay.GameThread;
import tigerislandserver.gameplay.TournamentScoreboard;
import tigerislandserver.gameplay.TournamentScoreboardData;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class GameThreadMock extends GameThread {

    private int cid;
    private DataLogger logger;
    private ArrayList<Tile> gameTiles;
    private ArrayList<TournamentPlayer> playersInGame;
    private int activePlayerIndex;
    private char gameID;
    private TournamentScoreboard scoreboard;
    private TournamentScoreboardData tourneyDataPlayer1;
    private TournamentScoreboardData tourneyDataPlayer2;
    private boolean gameNotEnded;
    private GameManager gameManager;
    private String endGameMessage;

    public GameThreadMock(TournamentPlayer player1, TournamentPlayer player2, ArrayList<Tile> tiles, char gameLetter, int cid, TournamentScoreboard scoreboard, long matchID){
        super(player1, player2, tiles, gameLetter, cid, scoreboard, matchID);
    }


}
