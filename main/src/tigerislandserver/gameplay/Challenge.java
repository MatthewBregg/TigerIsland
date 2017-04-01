package tigerislandserver.gameplay;

import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentClient;

import java.util.ArrayList;

public class Challenge {
    private Scheduler schedule;
    private ArrayList<TournamentClient> playerList;
    private TournamentScoreboard scoreboard;
    private ArrayList<Tile> currentDeck;
    private int roundNumber;

    public Challenge(ArrayList<TournamentClient> participants){
        playerList = participants;
        scoreboard = new TournamentScoreboard();
        schedule = new Scheduler(playerList.size());
        roundNumber = 0;
    }

    public void setMatchupType(MatchupType matchmaker){
        schedule.setTournamentType(matchmaker);
    }

    public void playRound(){
        // Generate TileDeck for round
        // initiate next round of matchups
    }

    public int getCurrentRound(){
        return roundNumber;
    }

    public int getRoundsRemaining(){
        return (schedule.getTotalRounds() - roundNumber);
    }
}
