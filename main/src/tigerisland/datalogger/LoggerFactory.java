package tigerisland.datalogger;


public class LoggerFactory {

    private static String dataBaseUrl = "jdbc:sqlite:tigersssss.db";

    synchronized public static DataLogger getLogger(int gameid, int challengeid) {
            DataLogger consoleLogger = new ConsoleLogger(challengeid,gameid);
            SQLiteLogger sqlLogger = new SQLiteLogger(challengeid,gameid,dataBaseUrl);
            sqlLogger.createTables();
            return new CompositeLogger(sqlLogger,consoleLogger);
        }

    public static String getDataBaseUrl() {
        return dataBaseUrl;
    }
}
