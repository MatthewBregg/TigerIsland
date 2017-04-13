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

    int getScoreForPlayerTurn(int challengeId, String userName, char gameId, int moveId);

    int getCurrentTurnNumber();

    String getOpponent(String s, int currentChallenge, int currentMatchInChallenge);

    int getVillagersForGame(int currentChallenge, String s, int currentMatchInChallenge, char a);

    int getTotoroForGame(int currentChallenge, String s, int currentMatchInChallenge, char a);

    int getTigerForGame(int currentChallenge, String s, int currentMatchInChallenge, char a);

    int getTotoroGame(int currentChallenge, String s, int currentMatchInChallenge, char b);
}
