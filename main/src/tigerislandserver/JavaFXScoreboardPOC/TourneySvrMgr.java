package tigerislandserver.JavaFXScoreboardPOC;

import javafx.collections.ObservableList;
import tigerisland.datalogger.LoggerFactory;
import tigerislandserver.TournamentVariables;
import tigerislandserver.server.TournamentPlayer;
import tigerislandserver.server.TournamentServer;

import java.sql.Connection;
import java.util.ArrayList;

public class TourneySvrMgr implements Runnable {
    private TournamentVariables tourneyVar;
    private TournamentServer tourneySvr;
    private int nbrOfChallenges;
    private String tourneyPassword;
    private String accessFilePath;
    private int accessTime;
    private int tourneyPort;
    private int tourneySeed;
    private ObservableList<TournamentScore> scores;

    public TourneySvrMgr(ObservableList<TournamentScore> scoreTable){
        scores = scoreTable;
        tourneyVar = TournamentVariables.getInstance();
        nbrOfChallenges = 3;
        tourneyPassword = "heygang";
        accessFilePath = "passwords.txt";
        accessTime = 30;
        tourneyPort = 6969;
        tourneySeed = 123456987;
    }

    public int getNbrOfChallenges() {
        return nbrOfChallenges;
    }

    public void setNbrOfChallenges(int nbrOfChallenges) {
        this.nbrOfChallenges = nbrOfChallenges;
    }

    public String getTourneyPassword() {
        return tourneyPassword;
    }

    public void setTourneyPassword(String tourneyPassword) {
        this.tourneyPassword = tourneyPassword;
    }

    public String getAccessFilePath() {
        return accessFilePath;
    }

    public void setAccessFilePath(String accessFilePath) {
        this.accessFilePath = accessFilePath;
    }

    public int getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(int accessTime) {
        this.accessTime = accessTime;
    }

    public int getTourneyPort() {
        return tourneyPort;
    }

    public void setTourneyPort(int tourneyPort) {
        this.tourneyPort = tourneyPort;
    }

    public int getTourneySeed() {
        return tourneySeed;
    }

    public void setTourneySeed(int tourneySeed) {
        this.tourneySeed = tourneySeed;
    }

    @Override
    public void run() {
        tourneySvr = new TournamentServer(tourneyPort);
        tourneySvr.acceptConnections(accessTime);
        if(tourneySvr.getPlayerCount() > 1) {
            collectTournamentScoreObjects(tourneySvr.getTourneyPlayers());
            tourneySvr.startTournament(nbrOfChallenges);
        } else {
            System.out.println("Not enough players!");
        }
    }

    private void collectTournamentScoreObjects(ArrayList<TournamentPlayer> players){
        for(TournamentPlayer player : players){
            TournamentScore playerScoreTracker = player.getTournamentScore();
            scores.add(playerScoreTracker);
        }
    }
}
