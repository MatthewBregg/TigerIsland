package tigerislandserver.gameplay;

import java.util.ArrayList;

public class TournamentSchedule {
    private int totalParticipants;
    private MatchupType matchmaker;

    public TournamentSchedule(int numOfPlayers){
        totalParticipants = numOfPlayers;
        matchmaker = new RoundRobinMatchup(this, totalParticipants);
    }

    public void setTournamentType(MatchupType matchmaker){
        if(matchmaker != null)
            this.matchmaker = matchmaker;
    }

    public ArrayList<Matchup> getMatchups(int round){
        return matchmaker.getMatchups(round);
    }

    public int getTotalRounds(){
        return matchmaker.getTotalRounds();
    }
}
