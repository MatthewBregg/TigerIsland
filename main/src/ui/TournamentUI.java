package ui;

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

    public TournamentUI() {

        LoggerFactory.createTables();
        Connection connection = LoggerFactory.getDbConnection();
        dataReader = new SQLiteReader(connection);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.BLUE);

        challengeLabel = new JLabel();
        challengeLabel.setText("Challenge");
        labelPanel.add(challengeLabel);

        matchInChallengeLabel = new JLabel();
        matchInChallengeLabel.setText("Match");
        labelPanel.add(matchInChallengeLabel);

        turnForMatchLabel = new JLabel();
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

    public static void main(String[] args) {
        new TournamentUI();

//        while (true) {
//            try {
//                sleep(1000);
//            } catch(InterruptedException e) {
//                // print out
//            }
//            updateUI();
//        }
    }

    private static void updateUI() {
        updateCurrentChallenge();
        updateCurrentMatch();
        updateTurnForCurrentMatch();
        updateTournamentScores();
    }

    private static void updateTournamentScores() {
        tournamentTable.updateTournamentScores(currentChallenge, currentMatchInChallenge, turnForCurrentMatch);
    }

    private static void updateTurnForCurrentMatch() {
        turnForCurrentMatch = dataReader.getCurrentTurnNumber();
        String challengeStr = String.format("Turn %s", turnForCurrentMatch);
        turnForMatchLabel.setText(challengeStr);
    }

    private static void updateCurrentMatch() {
        currentMatchInChallenge = dataReader.getCurrentMatchForChallenge(currentChallenge);
        String challengeStr = String.format("Match %s of %s", currentMatchInChallenge, 3);
        matchInChallengeLabel.setText(challengeStr);
    }

    private static void updateCurrentChallenge() {
        currentChallenge = dataReader.getCurrentChallengeBeenPlayed();
        String challengeStr = String.format("Challenge %s of %s", currentChallenge, 3);
        challengeLabel.setText(challengeStr);
    }

}
