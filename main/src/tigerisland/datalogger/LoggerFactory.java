package tigerisland.datalogger;


public class LoggerFactory {

    private static String dataBaseUrl = "jdbc:sqlite:tigersssss.db";

    synchronized public static DataLogger getLogger(int gameid, int challengeid, int matchid) {
            DataLogger consoleLogger = new ConsoleLogger(challengeid,gameid,matchid);
            SQLiteLogger sqlLogger = new SQLiteLogger(challengeid,gameid,matchid,dataBaseUrl);
            sqlLogger.createTables();
            return new CompositeLogger(sqlLogger,consoleLogger);
        }

    public static String getDataBaseUrl() {
        return dataBaseUrl;
    }
}
