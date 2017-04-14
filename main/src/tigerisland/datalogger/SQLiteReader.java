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

        String query = "select distinct player_id from overall_score order by player_id ASC";
        List<String> userNames = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                userNames.add( rs.getString("player_id"));
            }
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return userNames;
    }

    @Override
    public int getTeamTournamentScore(String teamName) {
       String query = String.format("select max(score) from tournament_score where player_id='%s'", teamName);
       int score = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                score = rs.getInt("max(score)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return score;
    }

    @Override
    public int getTeamScoreForChallenge(String teamName, int challengeId) {
       String query = String.format("select max(score) from overall_score " +
               "where player_id='%s' and challenge_id='%s'", teamName, challengeId);
       int score = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                score = rs.getInt("max(score)");
            }
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return score;
    }

    @Override
    public int getCurrentMatchForChallenge(int challengeId) {
        String query = String.format("select max(match_id) from matches " +
               "where challenge_id='%s'",challengeId);
       int matchId = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                matchId= rs.getInt("max(match_id)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return matchId;
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
    public int getScoreForPlayerTurn(int challengeId, String userName, char gameId, int moveId) {
        String query = String.format("select max(score) from game_turn_score where " +
                "challenge_id='%s' and player_id='%s' and game_id='%s' and move_id='%s'",
                challengeId, userName, gameId, moveId);

       int score = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                score = rs.getInt("max(score)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return score;
    }

    @Override
    public int getCurrentTurnNumber() {
       String query = "select max(move_id) from game_turn_score";
        int move_id = -1;
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                move_id = rs.getInt("max(move_id)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return move_id;
    }

    @Override
    public String getOpponent(String teamName, int currentChallenge, int currentMatchInChallenge) {
        String query = String.format("select p2_id from matches where " +
                "p1_id='%s' and challenge_id='%s' and match_id='%s'", teamName, currentChallenge, currentMatchInChallenge);

        String opponent = "NO_OPPONENT";
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                opponent = rs.getString("p2_id");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return opponent;
    }

    @Override
    public int getVillagersForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId) {
        return  -1;
    }

    @Override
    public int getTotoroForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId) {

        String query = String.format("select count(*) from build_action " +
                "where challenge_id='%s' and p_id='%s' and match_id='%s' and game_id='%s'",
               currentChallenge, teamName, currentMatchInChallenge, gameId);
        query += " and move_description like '%otoro%'";

        int totoro = 0;
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totoro = rs.getInt("count(*)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return  totoro;
    }

    @Override
    public int getTigerForGame(int currentChallenge, String teamName, int currentMatchInChallenge, char gameId) {
       String query = String.format("select count(*) from build_action " +
                "where challenge_id='%s' and p_id='%s' and match_id='%s' and game_id='%s'",
               currentChallenge, teamName, currentMatchInChallenge, gameId);
       query += " and move_description like '%iger%'";

        int tiger = 0;
        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                tiger = rs.getInt("count(*)");
            }

        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }

        return  tiger;
    }

    @Override
    public int getCurrentChallengeBeenPlayed() {
       String query = "select  max(challenge_id) from matches";
       int currentChallenge = -1;
       try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                currentChallenge = rs.getInt("max(challenge_id)");
            }
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }

        return currentChallenge;
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
