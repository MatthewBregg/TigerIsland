package tigerislandserver.gameplay;

import java.util.ArrayList;

public abstract class MatchupType {
    private Scheduler parent;

    public MatchupType(Scheduler parent){
        this.parent = parent;
    }

    public void setAlgorithm(MatchupType matchupType){
        parent.setTournamentType(matchupType);
    }

    public abstract ArrayList<Matchup> getMatchups(int round);
    public abstract int getTotalRounds();
    public abstract int getTotalMatches();
}
