package tigerisland.datalogger;


import java.util.List;
import java.util.Map;

public interface DataReader {

    int getCurrentChallengeBeenPlayed();

    List<String> getTeamNames();

    int getTeamTournamentScore(String teamName);

    int getTeamScoreForChallenge(String teamName, int challengeId);

    List<MatchRow> getAllMatches();

    Map<Integer, Map<Integer,Integer>> getPlayersScoresPerChallenge();

    Map<String, Integer> getTournamentScores();

    int getMostRecentChallengeScore(String userName);

    int getScoreForPlayerTurn(String userName, int turnId);

    int getCurrentTurnNumber();

}
