package ui.swing;

import tigerisland.datalogger.DataReader;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.datalogger.SQLiteReader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import static java.lang.Thread.sleep;

public class TournamentUI extends JFrame {

    private static DataReader dataReader;
    private static TournamentTable tournamentTable;
    private static JLabel challengeLabel;
    private static JLabel matchInChallengeLabel;
    private static JLabel turnForMatchLabel;
    private static JTextField challengeField;
    private static JTextField roundField;
    private static JTextField gameField;
    private static JTextField matchField;


    private static int currentChallenge = -1;
    private static int currentMatchInChallenge= -1;
    private static int turnForCurrentMatch = -1;

    private static int totalChallenges = -1;

    private static boolean mode = false;

    public TournamentUI(Connection connection) {

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null)
            defaults.put("Table.alternateRowColor", new Color(255, 212, 130));

        dataReader = new SQLiteReader(connection);

        JToggleButton modeButton = new JToggleButton("Manual Mode");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                mode = abstractButton.getModel().isSelected();
                System.out.println("Action - selected=" + mode + "\n");
            }
        };

        modeButton.addActionListener(actionListener);

        JToggleButton searchButton = new JToggleButton("Find Results");
        ActionListener actionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                System.out.println("Pulling Requested" + selected + "\n");
            }
        };
        searchButton.addActionListener(actionListener2);


        challengeField = new JTextField("Enter Challenge ID", 20);
        roundField = new JTextField("Enter round ID", 20);
        matchField = new JTextField("Enter match ID", 20);
        gameField = new JTextField("Enter game ID", 20);

        // creates the box at the top of the page
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(31, 64, 106));

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Welcome to the Tiger Island Tournament!");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font(titleLabel.getName(), Font.PLAIN, 50));

        labelPanel.add(titleLabel);

        labelPanel.add(searchButton, BoxLayout.Y_AXIS);
        labelPanel.add(modeButton, BoxLayout.Y_AXIS);
        labelPanel.add(challengeField, BoxLayout.Y_AXIS);
        labelPanel.add(roundField, BoxLayout.Y_AXIS);
        labelPanel.add(matchField, BoxLayout.Y_AXIS);
        labelPanel.add(gameField, BoxLayout.Y_AXIS);

        challengeLabel = new JLabel();
        challengeLabel.setText("Challenge");
        challengeLabel.setForeground(Color.white);
        labelPanel.add(challengeLabel, BorderLayout.SOUTH);

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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

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
            while(mode == false) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    // print out
                }
                updateUI();
            }
        }
    }

    private static void updateUI() {
        if (mode == false){
            updateCurrentChallenge();
            updateCurrentMatch();
            updateTurnForCurrentMatch();
            updateTournamentScores();
        }
        else if (mode == true){
            /* this is where we need to add code in order to update the information
            based on entered values
             */
            String challengeNumber = challengeField.getText();
            String roundNumber = roundField.getText();
            String matchNumber = matchField.getText();
            String gameNumber = gameField.getText();

            System.out.println(challengeNumber);
            System.out.println(roundNumber);
            System.out.println(matchNumber);
            System.out.println(gameNumber);
        }
        else{
            updateCurrentChallenge();
            updateCurrentMatch();
            updateTurnForCurrentMatch();
            updateTournamentScores();
        }
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
