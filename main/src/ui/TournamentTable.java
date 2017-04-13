package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TournamentTable {

    private JTable mainTable;
    private JTable overallScoreTable;
    private JTable gameAScoreTable;
    private JTable gameBScoreTable;
    private Object[][] tableList;

    private String[] columnNames = {
            "Team Names",
            "Tourney",
            "Challenge",
            "Opponent",};

    private Object[][] data = {
            {"Team_A", "200", "5", "Team_B"}
    };

    private String[] individualGamesColumns = {
            "Score",
            "V",
            "To",
            "Ti",};

    private Object[][] data2 = {
            {"20", "1", "2", 3}
    };

    private Object[][] data3 = {
            {45, 20, 12, 4}
    };

    private String[] names = {"Tournament", "GameA", "GameB"};

    public TournamentTable() {
        overallScoreTable = new JTable(data, columnNames);
        gameAScoreTable = new JTable(data2, individualGamesColumns);
        gameBScoreTable = new JTable(data3, individualGamesColumns);
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
