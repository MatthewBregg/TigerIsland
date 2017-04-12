package tigerisland.datalogger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoggerFactory {

    private static String dataBaseUrl = "jdbc:sqlite:tigersssss.db"; //"jdbc:sqlite:tigersssss.db"; For example

    private static Connection connection = null;

    synchronized public static DataLogger getLogger(char gameid, int challengeid, int matchid) {
            DataLogger consoleLogger = new ConsoleLogger(challengeid,gameid,matchid);
            SQLiteLogger sqlLogger = LoggerFactory.getSQLLogger(gameid,challengeid,matchid);
            return new CompositeLogger(sqlLogger,consoleLogger);
    }

    synchronized public static SQLiteLogger getSQLLogger(char gameid, int challengeid, int matchid) {
        if ( connection == null ) {
            openDatabaseConnection();
            createTables();
        }
        SQLiteLogger sqlLogger = new SQLiteLogger(challengeid,gameid,matchid, connection);
        return sqlLogger;
    }

    private static void openDatabaseConnection() {
        try {
            connection = DriverManager.getConnection(LoggerFactory.getDataBaseUrl());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void createTables() {
        if ( connection == null ) {
            openDatabaseConnection();
        }
        final String[] queries = new String[]{
                "CREATE TABLE IF NOT EXISTS matches (challenge_id integer not null, game_id charint not null, match_id integer not null, p1_id integer not null, p2_id integer not null, status string, primary key(challenge_id, game_id, match_id) );",
                "CREATE TABLE IF NOT EXISTS tiles_placed (challenge_id integer not null, game_id charint not null, match_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, orientation integer not null, tile text not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "CREATE TABLE IF NOT EXISTS build_action (challenge_id integer not null, game_id charint not null, match_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, move_description text not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "CREATE TABLE IF NOT EXISTS invalid_moves (challenge_id integer not null, game_id charint not null, match_id integer not null, turn_number integer not null, p_id integer not null, message string not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "create table IF NOT EXISTS raw_requests ( time_stamp integer primary key, request text not null);",
                "create table if not exists overall_score (challenge_id integer, player_id integer not null, score integer not null)",
        };

        try {
            for ( String query : queries ) {
                Statement stmt = connection.createStatement();
                synchronized (connection) {
                    stmt.execute(query);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clearTables() {
        if (connection == null ) {
            openDatabaseConnection();
        }
        final String[] queries = new String[]{
                "DELETE FROM matches;",
                "DELETE FROM tiles_placed;",
                "DELETE FROM build_action;",
                "DELETE FROM invalid_moves;",
                "DELETE FROM raw_requests;",
                "DELETE FROM overall_score;",
        };

        try {
            for ( String query : queries ) {
                Statement stmt = connection.createStatement();
                synchronized (connection) {
                    stmt.execute(query);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getDataBaseUrl() {
        return dataBaseUrl;
    }

    public static void setDataBaseUrl(String url) { dataBaseUrl = url; }

    public static Connection getDbConnection() {
        return connection;
    }
}
