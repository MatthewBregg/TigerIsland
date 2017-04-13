package ui;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class TournamentUI extends JFrame {

    private JTable mainTable;
    private static JLabel challengeLabel;
    private static JLabel matchInChallengeLabel;
    private static JLabel turnForMatchLabel;

    private static int currentChallenge = -1;
    private static int currentMatchInChallenge= -1;
    private static int turnForCurrentMatch = -1;

    public TournamentUI() {

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

        TournamentTable tournamentTable = new TournamentTable();
        mainTable = tournamentTable.getTournamentTable();

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
    }

    private static void updateTurnForCurrentMatch() {
        String challengeStr = String.format("Turn %s", turnForCurrentMatch);
        turnForMatchLabel.setText(challengeStr);
    }

    private static void updateCurrentMatch() {
        String challengeStr = String.format("Match %s of %s", currentMatchInChallenge, 3);
        matchInChallengeLabel.setText(challengeStr);
    }

    private static void updateCurrentChallenge() {
        String challengeStr = String.format("Challenge %s of %s", currentChallenge, 3);
        challengeLabel.setText(challengeStr);
    }

}
