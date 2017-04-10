package tigerislandserver.scoreboard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateScoreBoard {
    private final String url;
    private final String queryString = "SELECT * FROM MATCHES";
    private Connection connection;

    public GenerateScoreBoard(String url) {
        this.url = url;
    }

    class MatchRow {
        private int p1_id;
        private int p2_id;
        private int game_id;
        private int challenge_id;
        private String status;

        public MatchRow(int p1_id, int p2_id, int game_id, int challenge_id, String status) {
            this.p1_id = p1_id;
            this.p2_id = p2_id;
            this.game_id = game_id;

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
    }
    final String lineSeparator = System.getProperty("line.separator");
    public String getScoreBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getHTMLHeader());
        builder.append(this.getTableHeader());
        for (String tableRow : this.getTableBody() ) {
            builder.append(tableRow);
            builder.append(lineSeparator );
        }
        builder.append(this.getTableFooter());
        builder.append(this.getScoreBoardFooter());

        return builder.toString();
    }

    private String getScoreBoardFooter() {
        return "</body>" + lineSeparator + "</html>" + lineSeparator;
    }

    private String getTableFooter() {
        return "</table>" + lineSeparator;
    }

    private String getTableHeader() {
      return   "<table>" + lineSeparator +
                         "<tr>" + lineSeparator +
                                 "<th>Challenge ID</th>" + lineSeparator +
                                 "<th>Game ID</th>" + lineSeparator +
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

    private List<String> getTableBody() {
        List<String> table = new ArrayList<>();
        for ( MatchRow row : this.getMatches() ) {
            table.add(this.getTableRowFromMatchRow(row));
        }
        return table;
    }

    private String getTableRowFromMatchRow(MatchRow matchRow) {
        return "<tr>" +  getTableEntry(matchRow.getChallenge_id())
                + getTableEntry(matchRow.getGame_id())
                + getTableEntry(matchRow.p1_id) + getTableEntry(matchRow.getP2_id())
                + getTableEntry(matchRow.getStatus())
        + "</tr>";
    }

    private String getTableEntry(int val) {
        return "<td>" + val + "</td>";
    }

    private String getTableEntry(String val) {
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
            ResultSet rs = stmt.executeQuery(queryString);
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
           String status = rs.getString("status");
           matches.add(new MatchRow(p1_id,p2_id,game_id,challenge_id, status));
       }
    }

}
