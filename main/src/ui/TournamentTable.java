package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TournamentTable extends JTable{

    private JTable mainTable;
    private JTable overallScoreTable;
    private JTable gameAScoreTable;
    private JTable gameBScoreTable;
    private Object[][] tableList;

    private String[] tournamentColumnHeaders = {
            "Team Names",
            "Tourney",
            "Challenge",
            "Opponent",};

    private String[] gameAColumnHeaders = {
            "Score",
            "V",
            "To",
            "Ti",};

    private String[] gameBColumnHeaders = {
            "Score",
            "V",
            "To",
            "Ti",};

    private Object[][] tournamentColumnsValues = {
            {"Team A", "0", "0", "0"},
            {"Team B", "0", "0", "0"},
            {"Team C", "0", "0", "0"},
            {"Team D", "0", "0", "0"},
            {"Team E", "0", "0", "0"},
            {"Team F", "0", "0", "0"},
            {"Team G", "0", "0", "0"},
            {"Team H", "0", "0", "0"},
            {"Team I", "0", "0", "0"},
            {"Team J", "0", "0", "0"},
            {"Team K", "0", "0", "0"},
            {"Team L", "0", "0", "0"},
            {"Team M", "0", "0", "0"},
            {"Team N", "0", "0", "0"},
            {"Team O", "0", "0", "0"},
            {"Team P", "0", "0", "0"},
            {"Team Q", "0", "0", "0"},
            {"Team R", "0", "0", "0"},
            {"Team S", "0", "0", "0"},
            {"Team T", "0", "0", "0"},
    };

    private String[][] gameAColumnsValue = {
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
    };

    private String[][] gameBColumnsValue = {
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
            {"0", "0", "0", "0"},
    };


    private String[] names = {"Tournament", "GameA", "GameB"};

    public TournamentTable() {

        overallScoreTable = new JTable(tournamentColumnsValues, tournamentColumnHeaders);
        gameAScoreTable = new JTable(gameAColumnsValue, gameAColumnsValue);
        gameBScoreTable = new JTable(gameBColumnsValue, gameBColumnsValue);
        overallScoreTable.getTableHeader().setVisible(true);
        gameAScoreTable.getTableHeader().setVisible(true);
        gameBScoreTable.getTableHeader().setVisible(true);

        tableList = new Object[1][];
        tableList[0] = new Object[3];
        tableList[0][0] = overallScoreTable;
        tableList[0][1] = gameAScoreTable;
        tableList[0][2] = gameBScoreTable;

        mainTable = new JTable(new DefaultTableModel(tableList, names));

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
