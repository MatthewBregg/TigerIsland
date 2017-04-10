package tigerislandserver.scoreboard;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateScoreBoard {
    private final String url;
    private final String queryMatchString = "SELECT * FROM MATCHES";
    private Connection connection;
    private final String queryScoreString = "SELECT * FROM OVERALL_SCORE";

    public GenerateScoreBoard(String url) {
        this.url = url;
    }

    public String getScoreBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getHTMLHeader());
        builder.append(getScoreTable());
        builder.append(getMatchTable());
        builder.append(getHTMLFooter());
        return builder.toString();
    }

    public String getScoreTable() {
        StringBuilder builder = new StringBuilder();

        builder.append(getScoreTableHeader());

        for ( Map.Entry<Integer, Map<Integer, Integer>> kv1 : this.getScore().entrySet() )
        {
            for (Map.Entry<Integer, Integer> kv : kv1.getValue().entrySet())
            {
                builder.append("<tr>" + "<td>" + kv1.getKey() + "</td>" + "<td>" + kv.getKey() + "</td>" + "<td>" + kv.getValue() + "</td>" + "</th>");
                builder.append(lineSeparator);
            }
        }
        builder.append(getScoreTableFooter());

        return builder.toString();
    }

    private String getScoreTableHeader() {
         return lineSeparator + "<table>" + lineSeparator + "<tr> <th> ChallengeID </th> <th> PlayerID </th> <th> Score </th> </tr>" + lineSeparator ;
    }

    private String getScoreTableFooter() {
        return "</table>" + lineSeparator;
    }


    private Map<Integer, Map<Integer,Integer>> getScore() {
        Map<Integer, Map<Integer,Integer>> scores = new HashMap<>();
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryScoreString);
            putResultSetToScoreRows(rs,scores);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return scores;
    }

    private void putResultSetToScoreRows(ResultSet rs, Map<Integer, Map<Integer,Integer>> scores) throws SQLException {
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

    class MatchRow {
        private int match_id;
        private int p1_id;
        private int p2_id;
        private int game_id;
        private int challenge_id;
        private String status;

        public MatchRow(int p1_id, int p2_id, int game_id, int challenge_id, int match_id, String status) {
            this.p1_id = p1_id;
            this.p2_id = p2_id;
            this.game_id = game_id;
            this.match_id = match_id;

            this.challenge_id = challenge_id;
            this.status = status;
        }

        public int getChallenge_id() {
            return challenge_id;
        }

        public String getStatus() {
            return status;
        }

        public int getGame_id() {
            return game_id;
        }

        public int getP2_id() {
            return p2_id;
        }

        public int getP1_id() {
            return p1_id;
        }

        public int getMatch_id() {
            return match_id;
        }
    }
    final String lineSeparator = System.getProperty("line.separator");

    public String getMatchTable() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getMatchTableHeader());
        for (String tableRow : this.getMatchTableBody() ) {
            builder.append(tableRow);
            builder.append(lineSeparator );
        }
        builder.append(this.getMatchTableFooter());

        return builder.toString();
    }

    private String getHTMLFooter() {
        return "</body>" + lineSeparator + "</html>" + lineSeparator;
    }

    private String getMatchTableFooter() {
        return "</table>" + lineSeparator;
    }

    private String getMatchTableHeader() {
      return   "<table>" + lineSeparator +
                         "<tr>" + lineSeparator +
                                 "<th>Challenge ID</th>" + lineSeparator +
                                 "<th>Game ID</th>" + lineSeparator +
                                 "<th>Match ID</th>" + lineSeparator +
                                 "<th> Player 1 ID</th>" + lineSeparator +
                                 "<th> Player 2 ID</th>" + lineSeparator +
                                 "<th> Result </th>" + lineSeparator +
                         "</tr>" + lineSeparator;

    }

    private String getHTMLHeader() {
        return
               "<html>" + lineSeparator +
                    "<link href=\"https://fonts.googleapis.com/css?family=Bilbo+Swash+Caps|Cinzel\" rel=\"stylesheet\">" + lineSeparator +
                    "<link rel=\"stylesheet\" href=\"score.css\">" + lineSeparator +
                    "<meta http-equiv=\"refresh\" content=\"10;\">" + lineSeparator;

    }

    private List<String> getMatchTableBody() {
        List<String> table = new ArrayList<>();
        for ( MatchRow row : this.getMatches() ) {
            table.add(this.getMatchTableRowFromMatchRow(row));
        }
        return table;
    }

    private String getMatchTableRowFromMatchRow(MatchRow matchRow) {
        return "<tr>" +  getMatchTableEntry(matchRow.getChallenge_id())
                + getMatchTableEntry(matchRow.getGame_id())
                + getMatchTableEntry(matchRow.getMatch_id())
                + getMatchTableEntry(matchRow.p1_id) + getMatchTableEntry(matchRow.getP2_id())
                + getMatchTableEntry(matchRow.getStatus())
        + "</tr>";
    }

    private String getMatchTableEntry(int val) {
        return "<td>" + val + "</td>";
    }

    private String getMatchTableEntry(String val) {
        return "<td>" + val + "</td>";
    }

    private List<MatchRow> getMatches() {
        List<MatchRow> matches = new ArrayList<MatchRow>();
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryMatchString);
            putResultSetToMatchRows(rs,matches);
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return matches;
    }

    private void putResultSetToMatchRows(ResultSet rs, List<MatchRow> matches) throws SQLException {
       while(rs.next()) {
           int p1_id = rs.getInt("p1_id");
           int p2_id = rs.getInt("p2_id");
           int game_id = rs.getInt("game_id");
           int challenge_id = rs.getInt("challenge_id");
           int match_id = rs.getInt("match_id");
           String status = rs.getString("status");
           matches.add(new MatchRow(p1_id,p2_id,game_id,challenge_id, match_id, status));
       }
    }

}
