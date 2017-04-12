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
        //builder.append(getScoreTable());
        builder.append(getIDBoxContainerStart());
        builder.append(getChallengeIDBox());
        builder.append(getMatchIDBox());
        builder.append(getTurnIDBox());
        builder.append(getIDBoxContainerEnd());
        builder.append(getDivider());
        //builder.append(getMatchTable());
        builder.append(getTournamentScoreTableHeader());
        builder.append(getTournamentScoreTableFooter());
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

    private String getMatchTableEntry(char val) {
        return "<td>" + val + "</td>";
    }

    private String getMatchTableEntry(String val) {
        return "<td>" + val + "</td>";
    }

    private String getTurnIDBox(){
        return
                "<div class = 'col-md-3 col-md-offset-1 panel' style = 'background-color: #f7931e; padding-bottom: 10px; padding-top: 10px' height = '30px'>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<center>" +
                                "TURN " +
                            "</center>" + lineSeparator +
                        "</div>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<div class = 'col-lg-4'>" +
                                "TURN " +
                            "</div>" + lineSeparator +
                        "</div>" + lineSeparator +
                    "</div>" + lineSeparator;
    }

    private String getChallengeIDBox(){
        return
                "<div class = 'col-md-3 col-md-offset-1 panel' style = 'background-color: #f7931e; padding-bottom: 10px; padding-top: 10px' height = '30px'>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<center>" +
                                    "CHALLENGE " +
                            "</center>" + lineSeparator +
                        "</div>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<div class = 'col-lg-4'>" +
                                "CHALLENGE " +
                            "</div>" + lineSeparator +
                        "</div>" + lineSeparator +
                "</div>" + lineSeparator;
    }

    private String getMatchIDBox(){
        return
                "<div class = 'col-md-3 panel col-md-offset-1' style = 'background-color: #f7931e; padding-bottom: 10px; padding-top: 10px' height = '30px'>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<center>" +
                                "MATCH " +
                            "</center>" + lineSeparator +
                        "</div>" + lineSeparator +
                        "<div class = 'panel-font row' >" + lineSeparator +
                            "<div class = 'col-lg-4'>" +
                                "CHALLENGE " +
                            "</div>" + lineSeparator +
                        "</div>" + lineSeparator +
                    "</div>" + lineSeparator;
    }

    private String getIDBoxContainerStart(){
        return
                "<div class = 'container'>" + lineSeparator;
    }

    private String getIDBoxContainerEnd(){
        return
                "</div>" +lineSeparator;
    }

    private String getDivider(){
        return "<hr>" + lineSeparator;
    }

    private String getTournamentScoreTableFooter() {
        return "</table>" + lineSeparator + "</div>" + lineSeparator;
    }

    private String getTournamentScoreTableHeader() {
        return   "<div class = 'container'>" + lineSeparator +
                "<table border = '1'>" + lineSeparator +
                "<tr>" + lineSeparator +
                    "<th></th>" + lineSeparator +
                    "<th colspan='2'><center>Tournament Points </center></th>" + lineSeparator +
                    "<th colspan='3'> <center>Current Match</center> </th>" + lineSeparator +
                "</tr>" + lineSeparator +
                "<tr>" + lineSeparator +
                    "<th></th>" + lineSeparator +
                    "<th><center>Overall In</center></th>" + lineSeparator +
                    "<th><center>In Current</center></th>" + lineSeparator +
                    "<th colspan='3'> <center>For This Turn</center></th>" + lineSeparator +
                "</tr>" + lineSeparator +
                "<tr>" + lineSeparator +
                    "<th><center>Team Name</center></th>" + lineSeparator +
                    "<th><center>Tourney</center></th>" + lineSeparator +
                    "<th><center>Challenge</center></th>" + lineSeparator +
                    "<th> <center>Opponent</center></th>" + lineSeparator +
                    "<th> <center>Game A</center></th>" + lineSeparator +
                    "<th> <center>Game B</center></th>" + lineSeparator +
                "</tr>" + lineSeparator;

    }

}
