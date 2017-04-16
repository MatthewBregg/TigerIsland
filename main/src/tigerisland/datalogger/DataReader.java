package tigerisland.datalogger;


import java.util.List;
import java.util.Map;

public interface DataReader {

    int getCurrentChallengeBeenPlayed();

    List<String> getTeamNames();

    int getTeamTournamentScore(String teamName);

    int getTeamScoreForChallenge(String teamName, int challengeId);

    int getCurrentMatchForChallenge(int challengeId);

    List<MatchRow> getAllMatches();

    Map<Integer, Map<Integer,Integer>> getPlayersScoresPerChallenge();

    Map<String, Integer> getTournamentScores();

    int getMostRecentChallengeScore(String userName);

    int getScoreForPlayerGame(int challengeId, String userName, int matchId, char gameId);

    int getCurrentTurnNumber();

    String getOpponent(String s, int currentChallenge, int currentMatchInChallenge);

    int getVillagersForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId);

    int getTotoroForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId);

    int getTigerForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId);

    int getPlayerLatestMatch(String player);

}
