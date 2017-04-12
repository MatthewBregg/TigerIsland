package tigerisland.datalogger;


import java.util.List;
import java.util.Map;

public interface DataReader {

    List<MatchRow> getAllMatches();

    Map<Integer, Map<Integer,Integer>> getPlayersScoresPerChallenge();

    Map<String, Integer> getTournamentScores();

    List<String> getTeamNames();

    int getMostRecentChallengeScore(String userName);

    int getScoreForPlayerTurn(String userName, int turnId);

    int getCurrentTurnNumber();
}
