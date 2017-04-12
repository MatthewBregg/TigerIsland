package ui;

import tigerisland.datalogger.DataReader;
import tigerisland.datalogger.MatchRow;

import java.util.ArrayList;
import java.util.List;

public class GenerateScoreBoard {

    private DataReader dataReader;

    final String lineSeparator = System.getProperty("line.separator");

    public GenerateScoreBoard(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public String getScoreBoard() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getHTMLHeader());
        builder.append(getRoundNumberSection());
        //builder.append(getScoreTable());
        builder.append(getMatchTable());
        builder.append(getHTMLFooter());
        return builder.toString();
    }

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
        return "</table>" + lineSeparator + "</div>" + lineSeparator;
    }

    private String getMatchTableHeader() {
      return   "<div class = 'container'>" + lineSeparator +
              "<table border = '1'>" + lineSeparator +
                         "<tr>" + lineSeparator +
                                 "<th><center>Challenge ID</center></th>" + lineSeparator +
                                 "<th><center>Game ID</center></th>" + lineSeparator +
                                 "<th><center>Match ID</center></th>" + lineSeparator +
                                 "<th> <center>Player 1 ID</center></th>" + lineSeparator +
                                 "<th> <center>Player 2 ID</center></th>" + lineSeparator +
                                 "<th> <center>RESULT </center></th>" + lineSeparator +
                         "</tr>" + lineSeparator;

    }

    private String getHTMLHeader() {
        return
               "<html>" + lineSeparator +
                    "<head>" + lineSeparator +
                    "<link href=\"https://fonts.googleapis.com/css?family=Bilbo+Swash+Caps|Cinzel\" rel=\"stylesheet\">" + lineSeparator +
                    "<link href=\"https://fonts.googleapis.com/css?family=Macondo|Open+Sans\" rel=\"stylesheet\">" + lineSeparator +
                    "<<link href=\"https://fonts.googleapis.com/css?family=Anton\" rel=\"stylesheet\">" + lineSeparator +
                   "<style>\n" +
                       "@import url('https://fonts.googleapis.com/css?family=PT+Sans+Narrow');\n" +
                       "</style>" + lineSeparator +
                    "<link rel=\"stylesheet\" href=\"css/score.css\">" + lineSeparator +
                    "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css>" + lineSeparator +
                    "<link rel='stylesheet' type='text/css' href='css/index.css' />" + lineSeparator +
                    "<link rel='stylesheet' type='text/css' href='css/bootstrap/css/bootstrap.min.css'>" + lineSeparator +
                    "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>" + lineSeparator +
                    "<meta http-equiv=\"refresh\" content=\"10;\">" + lineSeparator +
                    "<center> <h1 class = 'special-font' style = 'padding-top: 20px;'> <strong> TIGER ISLAND T<img class = 'icon' src = 'images/tigerLogo.png'>URNAMENT</strong> </h1> </center>" + lineSeparator +
                    "<hr>" + lineSeparator +
                    "</head>" + lineSeparator;
    }

    private String getRoundNumberSection(){
        return
                "<body>" + lineSeparator +
                "<h3 class= 'header-font'><center> Round 5 out of 20 </center></h3>" + lineSeparator +
                "<hr>" + lineSeparator;
    }

    private List<String> getMatchTableBody() {
        List<String> table = new ArrayList<>();
        for ( MatchRow row : dataReader.getAllMatches() ) {
            table.add(this.getMatchTableRowFromMatchRow(row));
        }
        return table;
    }

    private String getMatchTableRowFromMatchRow(MatchRow matchRow) {
        return "<tr>" +  getMatchTableEntry(matchRow.getChallenge_id())
                + getMatchTableEntry(matchRow.getGame_id())
                + getMatchTableEntry(matchRow.getMatch_id())
                + getMatchTableEntry(matchRow.getP1_id()) + getMatchTableEntry(matchRow.getP2_id())
                + getMatchTableEntry(matchRow.getStatus())
        + "</tr>";
    }

    private String getMatchTableEntry(int val) {
        return "<td>" + val + "</td>";
    }

    private String getMatchTableEntry(String val) {
        return "<td>" + val + "</td>";
    }
}
