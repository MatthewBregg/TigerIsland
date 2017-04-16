package ui.swing;

import tigerisland.datalogger.DataReader;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.datalogger.SQLiteReader;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

import static java.lang.Thread.sleep;

public class TournamentUI extends JFrame {

    private static DataReader dataReader;
    private static TournamentTable tournamentTable;
    private static JLabel challengeLabel;
    private static JLabel matchInChallengeLabel;
    private static JLabel turnForMatchLabel;

    private static int currentChallenge = -1;
    private static int currentMatchInChallenge= -1;
    private static int turnForCurrentMatch = -1;

    private static int totalChallenges = -1;

    public TournamentUI(Connection connection) {

        dataReader = new SQLiteReader(connection);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(31, 64, 106));

        challengeLabel = new JLabel();

        challengeLabel.setText("Challenge");
        challengeLabel.setForeground(Color.white);
        labelPanel.add(challengeLabel);

        matchInChallengeLabel = new JLabel();
        matchInChallengeLabel.setForeground(Color.white);
        matchInChallengeLabel.setText("Match");
        labelPanel.add(matchInChallengeLabel);

        turnForMatchLabel = new JLabel();
        turnForMatchLabel.setForeground(Color.white);
        turnForMatchLabel.setText("Turn");
        labelPanel.add(turnForMatchLabel);

        tournamentTable = new TournamentTable(dataReader);
        JTable mainTable = tournamentTable.getTournamentTable();

        Container container = this.getContentPane();
        mainPanel.add(labelPanel);
        mainPanel.add(new JScrollPane(mainTable));
        container.add(mainPanel);

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.pack();
        setSize(500,500);
        setVisible(true);
    }

    public static void main(String args[]){

        totalChallenges = args.length >= 1 ? Integer.parseInt(args[0]) : 0;
        LoggerFactory.createTables();
        Connection connection = LoggerFactory.getDbConnection();
        startScoreBoard(connection, totalChallenges);
    }

    public static void startScoreBoard(Connection connection, int numberOfChallengesToPlay) {
        totalChallenges  = numberOfChallengesToPlay;
        new TournamentUI(connection);

        while (true) {
            try {
                sleep(1000);
            } catch(InterruptedException e) {
                // print out
            }
            updateUI();
        }
    }

    private static void updateUI() {
        updateCurrentChallenge();
        updateCurrentMatch();
        updateTurnForCurrentMatch();
        updateTournamentScores();
    }

    private static void updateTournamentScores() {
        tournamentTable.updateTournamentScores(currentChallenge, currentMatchInChallenge, 4);
    }

    private static void updateTurnForCurrentMatch() {
        turnForCurrentMatch = dataReader.getCurrentTurnNumber();
        String challengeStr = String.format("Turn %s", turnForCurrentMatch);
        turnForMatchLabel.setText(challengeStr);
    }

    private static void updateCurrentMatch() {
        currentMatchInChallenge = dataReader.getCurrentMatchForChallenge(currentChallenge);
        String challengeStr = String.format("Match %s of %s", currentMatchInChallenge+1, 3 );
        matchInChallengeLabel.setText(challengeStr);
    }

    private static void updateCurrentChallenge() {
        currentChallenge = dataReader.getCurrentChallengeBeenPlayed();
        String challengeStr = String.format("Challenge %s of %s", currentChallenge+1, totalChallenges);
        challengeLabel.setText(challengeStr);
    }

}
