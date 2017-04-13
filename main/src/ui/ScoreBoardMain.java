package ui;

import tigerisland.datalogger.DataReader;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.datalogger.SQLiteReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class ScoreBoardMain {
    private final static String FILENAME = "./ScoreBoard.html";
    public static void main(String[] args) {
        LoggerFactory.createTables();
        while(true) {

            DataReader sqliteDataReader = new SQLiteReader(LoggerFactory.getDbConnection());
            GenerateScoreBoard scoreBoardGenerator = new GenerateScoreBoard(sqliteDataReader);

            String scoreboard = (scoreBoardGenerator.getScoreBoard());

            BufferedWriter bufferedWriter = null;
            BufferedWriter bufferedWriterOverall = null;

            FileWriter fileWriter = null;
            FileWriter fileWriterOverall = null;
            try {
                fileWriter = new FileWriter(FILENAME);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(scoreboard);
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                   if (fileWriter != null) {
                        fileWriter.close();
                    }
               } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            try {
                sleep(5000);
            } catch (Exception ex) {
                System.out.print(ex);
            }
        }
    }


}
