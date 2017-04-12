package tigerisland.datalogger;

import tigerisland.player.PlayerID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteReader implements DataReader{

    private Connection connection;

    private final  String getTournamentScoresQuery = "SELECT * FROM TOURNAMENT_SCORE";
    private final  String getTournamentPlayersQuery = "SELECT player_id FROM TOURNAMENT_SCORE ";
    private final String queryMatchString = "SELECT * FROM MATCHES";
    private final String overallScoreQuery = "SELECT * FROM OVERALL_SCORE";

    public SQLiteReader(Connection dbConnection) {
        this.connection = dbConnection;
    }

    @Override
    public List<MatchRow> getAllMatches() {
        List<MatchRow> matches = new ArrayList<MatchRow>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryMatchString);
            addResultSetToMatchRows(rs, matches);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return matches;
    }

    @Override
    public Map<Integer, Map<Integer, Integer>> getPlayersScoresPerChallenge() {
        Map<Integer, Map<Integer,Integer>> scores = new HashMap<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(overallScoreQuery);
            addResultSetToScoreRows(rs,scores);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return scores;
    }

    @Override
    public Map<String, Integer> getTournamentScores() {
        Map<String, Integer> scores = new HashMap<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getTournamentScoresQuery);
            scores = getTournamentScores(rs);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return scores;
    }

    @Override
    public List<String> getTeamNames() {
        List<String> userNames = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getTournamentPlayersQuery);
            userNames = getTournamentPlayers(rs);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return userNames;
    }

    @Override
    public int getMostRecentChallengeScore(String userName) {
        String query =
                String.format("SELECT SCORE, MAX(CHALLENGE_ID) FROM OVERALL_SCORE WHERE PLAYER_ID = %s)", userName);

        int score = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                score = rs.getInt("score");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return score;
    }

    @Override
    public int getScoreForPlayerTurn(String userName, int turnId) {
        return 0;
    }

    @Override
    public int getCurrentTurnNumber() {
       String query = "SELECT MAX(move_id) FROM GAME_TURN_SCORE";
        int move_id = -1;
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                move_id = rs.getInt("move_id");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return move_id;
    }

    private void addResultSetToMatchRows(ResultSet rs, List<MatchRow> matches) throws SQLException {
       while(rs.next()) {
           String p1_id = rs.getString("p1_id");
           String p2_id = rs.getString("p2_id");
           char game_id = rs.getString("game_id").charAt(0);
           int challenge_id = rs.getInt("challenge_id");
           int match_id = rs.getInt("match_id");
           String status = rs.getString("status");
           matches.add(new MatchRow(p1_id,p2_id,game_id,challenge_id, match_id, status));
       }
    }

    private void addResultSetToScoreRows(ResultSet rs, Map<Integer, Map<Integer,Integer>> scores) throws SQLException {
        while(rs.next()) {
            int cid = rs.getInt("challenge_id");
            int p_id = rs.getInt("player_id");
            int score = rs.getInt("score");

            if(scores.get(cid) == null)
            {
                HashMap<Integer,Integer> map=new HashMap<>();
                map.put(p_id, score);
                scores.put(cid, map);
            }
            else
            {
                scores.get(cid).put(p_id, score);
            }
        }
    }

    private Map<String,Integer> getTournamentScores(ResultSet rs) throws SQLException {
        Map<String, Integer> scores = new HashMap<>();
        while(rs.next()) {
           String playerUserName = rs.getString("player_id");
           int score = rs.getInt("score");
           scores.put(playerUserName, score);
       }
       return scores;
    }

    private List<String> getTournamentPlayers(ResultSet rs) throws SQLException {
        List<String> userNames = new ArrayList<>();
        while(rs.next()) {
           String playerUserName = rs.getString("player_id");
           userNames.add(playerUserName);
       }
       return userNames;
    }

}
