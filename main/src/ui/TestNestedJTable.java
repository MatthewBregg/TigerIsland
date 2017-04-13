package ui;

import java.awt.Component;
        import java.awt.Container;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseMotionListener;

        import javax.swing.AbstractCellEditor;
        import javax.swing.JFrame;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.table.DefaultTableCellRenderer;
        import javax.swing.table.DefaultTableModel;
        import javax.swing.table.TableCellEditor;
        import javax.swing.table.TableColumn;
        import javax.swing.table.TableColumnModel;


public class TestNestedJTable extends JFrame{

    private JTable mainTable;
    private JTable overallScoreTable;
    private JTable gameAScoreTable;
    private JTable gameBScoreTable;
    private Object[][] tableList;
    private Container container;

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


    //private String[] names = {"Tournament", "GameA", "GameB", "X"};
    private String[] names = {"Tournament", "GameA", "GameB"};

    public TestNestedJTable(){
        container = this.getContentPane();

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
        mainTable.addMouseMotionListener(new MyMouseMotionListener());

        // Enable the ability to select a single cell
        mainTable.setColumnSelectionAllowed(true);
        mainTable.setRowSelectionAllowed(true);

        container.add(new JScrollPane(mainTable));

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.pack();
        setSize(500,500);
        setVisible(true);
    }


    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 4415155875184525824L;
        JTable table;

        CustomTableCellRenderer(JTable table){
            this.table=table;
            this.table.setOpaque(true);
            this.table.setAlignmentY(JTable.LEFT_ALIGNMENT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            this.table=(JTable)value;
            return new JScrollPane(this.table);
        }
    }

    class CustomTableCellEditor extends AbstractCellEditor implements TableCellEditor{

        JTable table;

        CustomTableCellEditor(JTable table){
            this.table=table;
            this.table.setOpaque(true);
            this.table.setAlignmentY(JTable.CENTER_ALIGNMENT);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value, boolean isSelected, int row, int column) {
            this.table=(JTable)value;
            return new JScrollPane(this.table);
        }

        @Override
        public Object getCellEditorValue() {
            return this.table;
        }

    }

    class MyMouseMotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(e.getComponent() instanceof JTable){
                JTable tempTable = (JTable)e.getComponent();

                TableColumnModel columnModel = tempTable.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = columnModel.getColumn(viewColumn).getModelIndex();
                int row = tempTable.rowAtPoint(e.getPoint());

                // change the if-statement to the columns you want to programmatically
                // enter edit mode of, when the mouse is over it.
                if(column==0 || column==1){
                    // Set the cell on the row and column in edit mode
                    boolean success = tempTable.editCellAt(row, column);
                    if (success) {
                        boolean toggle = false;
                        boolean extend = false;
                        // Select cell
                        tempTable.changeSelection(row, column, toggle, extend);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        new TestNestedJTable();
    }
}
