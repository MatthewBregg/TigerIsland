package ui;

import tigerisland.datalogger.DataReader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TournamentTable {

    private DataReader dataReader;
    private JTable mainTable;
    private JTable overallScoreTable;
    private JTable gameAScoreTable;
    private JTable gameBScoreTable;
    private Object[][] tableList;

    private String[] mainTableColumns = {"Tournament", "GameA", "GameB"};

    private String[] tournamentTableColumns = {
            "Team Names",
            "Tourney",
            "Challenge",
            "Opponent",};

    private String[] individualGamesColumns = {
            "Score",
            "V",
            "To",
            "Ti",};

    private Object[][] tournamentTableData = {
            {"Team_A", "200", "5", "Team_B"}
    };

    private Object[][] gameATableData = {
            { -1, -1, -1}
    };

    private Object[][] gameBTableData = {
            {-2, -2, -2, -2}
    };

    public TournamentTable(DataReader dataReader) {

        this.dataReader = dataReader;
        overallScoreTable = new JTable(tournamentTableData, tournamentTableColumns);
        gameAScoreTable = new JTable(gameATableData, individualGamesColumns);
        gameBScoreTable = new JTable(gameBTableData, individualGamesColumns);
        overallScoreTable.getTableHeader().setVisible(true);
        gameAScoreTable.getTableHeader().setVisible(true);
        gameBScoreTable.getTableHeader().setVisible(true);

        tableList = new Object[1][];
        tableList[0] = new Object[3];
        tableList[0][0] = overallScoreTable;
        tableList[0][1] = gameAScoreTable;
        tableList[0][2] = gameBScoreTable;

        mainTable = new JTable(new DefaultTableModel(tableList, mainTableColumns));

        TableColumn tc = mainTable.getColumnModel().getColumn(0);
        tc.setCellRenderer(new CustomTableCellRenderer(overallScoreTable));

        tc = mainTable.getColumnModel().getColumn(1);
        tc.setCellRenderer(new CustomTableCellRenderer(gameAScoreTable));

        tc = mainTable.getColumnModel().getColumn(2);
        tc.setCellRenderer(new CustomTableCellRenderer(gameBScoreTable));

        mainTable.setRowHeight(overallScoreTable.getPreferredSize().height+ overallScoreTable.getTableHeader().getPreferredSize().height+4);
    }

    public JTable getTournamentTable() {
        return mainTable;
    }

    public void updateTournamentScores(int currentChallenge, int currentMatchInChallenge) {

        List<String> teamNames = dataReader.getTeamNames();

        for(String teamName : teamNames) {

        }

        Map<String, Integer> tournamentScores = dataReader.getTournamentScores();


        gameBTableData[0][1] = 10000;
        mainTable.updateUI();
    }

    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        JTable table;
        CustomTableCellRenderer(JTable table){
            this.table = table;
            this.table.setOpaque(true);
            this.table.setAlignmentY(JTable.CENTER_ALIGNMENT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            this.table = (JTable)value;
            return new JScrollPane(this.table);
        }
    }
}
