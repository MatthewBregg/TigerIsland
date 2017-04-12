package tigerisland.datalogger;

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

    private void addResultSetToMatchRows(ResultSet rs, List<MatchRow> matches) throws SQLException {
       while(rs.next()) {
           int p1_id = rs.getInt("p1_id");
           int p2_id = rs.getInt("p2_id");
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

}
