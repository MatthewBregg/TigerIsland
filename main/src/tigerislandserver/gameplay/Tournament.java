package tigerislandserver.gameplay;

import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentClient;

import java.util.ArrayList;

public class Tournament {
    private TournamentSchedule schedule;
    private ArrayList<TournamentClient> playerList;
    private TournamentScoreboard scoreboard;
    private ArrayList<Tile> currentDeck;
    private int roundNumber;

    public Tournament(ArrayList<TournamentClient> participants){
        playerList = participants;
        scoreboard = new TournamentScoreboard();
        schedule = new TournamentSchedule(playerList.size());
        roundNumber = 0;
    }

    public void setMatchupType(MatchupType matchmaker){
        schedule.setTournamentType(matchmaker);
    }

    public void playRound(){
        // initiate next round of matchups
    }

    public int getCurrentRound(){
        return roundNumber;
    }

    public int getRoundsRemaining(){
        return (schedule.getTotalRounds() - roundNumber);
    }
}
