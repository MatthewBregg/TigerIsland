package tigerisland.datalogger;

import tigerisland.player.PlayerID;

import java.util.List;
import java.util.Map;

public interface DataReader {

    List<MatchRow> getAllMatches();

    Map<Integer, Map<Integer,Integer>> getPlayersScoresPerChallenge();

    Map<String, Integer> getTournamentScores();

    List<String> getTeamNames();

    int getScoreForPlayerTurn(PlayerID pid, int turnId);

    int getCurrentTurnNumber();
}
