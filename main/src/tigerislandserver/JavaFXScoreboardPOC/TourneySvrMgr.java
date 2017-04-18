package tigerislandserver.JavaFXScoreboardPOC;

import javafx.collections.ObservableList;
import tigerislandserver.TournamentVariables;
import tigerislandserver.server.TournamentPlayer;
import tigerislandserver.server.TournamentServer;

import java.util.ArrayList;

public class TourneySvrMgr implements Runnable {
    private TournamentVariables tourneyVar;
    private TournamentServer tourneySvr;
    private int accessTime;
    private int tourneyPort;
    private ObservableList<TournamentScore> scores;
    private ObservableList<RoundInfo> roundInfo;

    public TourneySvrMgr(ObservableList<TournamentScore> scoreTable, ObservableList<RoundInfo> roundInfos ){
        tourneyVar = TournamentVariables.getInstance();
        roundInfo = roundInfos;
        scores = scoreTable;
        tourneyVar = TournamentVariables.getInstance();
        tourneyVar.setNumberOfChallenges(3);
        tourneyVar.setTournamentPassword("heygang");
        tourneyVar.setUsernamePasswordFile("pass.txt");
        accessTime = 30;
        tourneyPort = 6969;
        tourneyVar.setRandomSeed(123456987);
    }

    public int getNbrOfChallenges() {
        return tourneyVar.getNumberOfChallenges();
    }

    public void setNbrOfChallenges(int nbrOfChallenges) {
       tourneyVar.setNumberOfChallenges(nbrOfChallenges);
    }

    public String getTourneyPassword() {
        return tourneyVar.getTournamentPassword();
    }

    public void setTourneyPassword(String tourneyPassword) {
        tourneyVar.setTournamentPassword(tourneyPassword);
    }

    public String getAccessFilePath() {
        return tourneyVar.getUsernamePasswordFile();
    }

    public void setAccessFilePath(String accessFilePath) {
        tourneyVar.setUsernamePasswordFile(accessFilePath);
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
        return tourneyVar.getRandomSeed();
    }

    public void setTourneySeed(int tourneySeed) {
        tourneyVar.setRandomSeed(tourneySeed);
    }

    @Override
    public void run() {
        scores.remove(0, scores.size());
        tourneySvr = new TournamentServer(tourneyPort);
        tourneySvr.acceptConnections(accessTime);
        if(tourneySvr.getPlayerCount() > 1) {
            collectTournamentScoreObjects(tourneySvr.getTourneyPlayers());
            roundInfo.add(tourneySvr.getTrackRoundInfo());
            tourneySvr.startTournament(getNbrOfChallenges());

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
