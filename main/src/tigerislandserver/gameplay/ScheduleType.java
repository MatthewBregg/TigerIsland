package tigerislandserver.gameplay;

import java.util.ArrayList;

public abstract class ScheduleType {
    private Scheduler parent;

    public ScheduleType(Scheduler parent){
        this.parent = parent;
    }

    public void setAlgorithm(ScheduleType scheduleType){
        parent.setTournamentType(scheduleType);
    }

    public abstract ArrayList<Matchup> getMatchups(int round);
    public abstract int getTotalRounds();
    public abstract int getTotalMatches();
}
