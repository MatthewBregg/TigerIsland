package tigerisland.datalogger;


public class LoggerFactory {

    private static String dataBaseUrl = "jdbc:sqlite:tigersssss.db";

    synchronized public static DataLogger getLogger(int gameid, int challengeid, int matchid) {
            DataLogger consoleLogger = new ConsoleLogger(challengeid,gameid,matchid);
            SQLiteLogger sqlLogger = LoggerFactory.getSQLLogger(gameid,challengeid,matchid);
            return new CompositeLogger(sqlLogger,consoleLogger);
    }

    synchronized public static SQLiteLogger getSQLLogger(int gameid, int challengeid, int matchid) {
        SQLiteLogger sqlLogger = new SQLiteLogger(challengeid,gameid,matchid,dataBaseUrl);
        sqlLogger.createTables();
        return sqlLogger;

    }


    public static String getDataBaseUrl() {
        return dataBaseUrl;
    }
}
