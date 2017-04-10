package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.sql.*;

public class SQLiteLogger implements DataLogger {
    private int challengeId;
    private int gameId;
    private int turnNumber = 0;
    private int matchId;

    private Connection connection = null;
    private String url = null;

    private boolean hasError = false;

    public boolean hasErrored() {
        return hasError;
    }

    public void clearError() {
        hasError = false;
    }
    public SQLiteLogger(int challengeId, int gameId, int matchId, String url) {
        this.matchId = matchId;
        this.challengeId = challengeId;
        this.gameId = gameId;
        this.url = url; //"jdbc:sqlite:tigersssss.db"; For example
        openDatabaseConnection();;
    }

    private void writeToBuildActions(PlayerID pid, Location loc, String move_description) {
        String query = "INSERT INTO build_action(challenge_id,game_id,match_id,turn_number,p_id,loc_x,loc_y,loc_z,move_description) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setInt(2, gameId);
            prstmnt.setInt(3,matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setInt(5,pidToInt(pid));
            prstmnt.setInt(6,loc.getX());
            prstmnt.setInt(7,loc.getY());
            prstmnt.setInt(8,loc.getZ());
            prstmnt.setString(9,move_description);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }

    }

    private int pidToInt(PlayerID pid) {
        return pid.getId();
    }

    private void writeToMatches(PlayerID p1, PlayerID p2, String status) {
        String query = "INSERT OR REPLACE INTO matches(challenge_id, game_id, match_id, p1_id, p2_id, status) VALUES(?,?,?,?,?,?)";
        System.out.println("Scoreboard : P1 " + p1 + " P2 " + p2 + " " + status);
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setInt(2, gameId);
            prstmnt.setInt(3,matchId);
            prstmnt.setInt(4, pidToInt(p1));
            prstmnt.setInt(5, pidToInt(p2));
            prstmnt.setString(6,status);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void writeToOverallScore(int cid, int p_id, int score) {
        String query = "INSERT OR REPLACE INTO overall_score(challenge_id,player_id,score) VALUES(?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, cid);
            prstmnt.setInt(2, p_id);
            prstmnt.setInt(3, score);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void writeToRawRequest(long timestamp, String message) {
        String query = "INSERT INTO raw_requests (time_stamp, request) VALUES(?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, (int)timestamp);
            prstmnt.setString(2, message);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void writeToTilesPlaced(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        String query = "INSERT INTO tiles_placed(challenge_id,game_id,match_id,turn_number,p_id,loc_x,loc_y,loc_z,orientation,tile) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setInt(2, gameId);
            prstmnt.setInt(3,matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setInt(5,pidToInt(pid));
            prstmnt.setInt(6,loc.getX());
            prstmnt.setInt(7,loc.getY());
            prstmnt.setInt(8,loc.getZ());
            prstmnt.setInt(9,orientation.getAngle());
            prstmnt.setString(10,tileTerrains);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void openDatabaseConnection() {
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }


    @Override
    public void writeRawRequest(long timeStamp, String message) {
        writeToRawRequest(timeStamp, message);
    }

    @Override
    public void writePlacedTotoroMove(PlayerID pid, Location loc) {
        writeToBuildActions(pid, loc, "Placed totoro");
    }

    @Override
    public void writeFoundedSettlementMove(PlayerID pid, Location loc) {
        writeToBuildActions(pid,loc,"Founded settlement");
    }

    @Override
    public void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain) {
        writeToBuildActions(pid,loc,"ExpandedSettlementToTerrainType:"+terrain);
    }

    @Override
    public void writePlacedTigerMove(PlayerID pid, Location loc) {
        writeToBuildActions(pid,loc,"Placed Tiger");
    }

    @Override
    public void writePlacedTileMove(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        writeToTilesPlaced(pid,loc,orientation,tileTerrains);
    }

    @Override
    public void writeInvalidMoveAttempted(PlayerID pid, String message) {
        writeToInvalidMoves(pid,message);
    }

    @Override
    public void writeMoveResult(String message) {

    }

    private void writeToInvalidMoves(PlayerID pid, String message) {
        String query = "INSERT INTO invalid_moves(challenge_id,game_id,match_id,turn_number,p_id,message) VALUES(?,?,?,?,?,?)";
            try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setInt(2, gameId);
            prstmnt.setInt(3, matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setInt(5,pidToInt(pid));
            prstmnt.setString(6,message);
            prstmnt.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }


    @Override
    public void writeGameEnded(PlayerID winner, PlayerID loser, String matchEndCondition) {
        writeToMatches(winner,loser, matchEndCondition);
    }

    @Override
    public void writeGameStarted(PlayerID p1, PlayerID p2) {
        writeToMatches(p1,p2,"GameOngoing");
    }

    @Override
    public void nextTurn() {
        ++turnNumber;
    }

    public void newGame(int gameId, int challengeID) {
        this.gameId = gameId;
        this.challengeId = challengeID;
        turnNumber = 0;
    }

    public void setPlayerScore(int cid, PlayerID pid, int score) {
        writeToOverallScore(cid, pid.getId(),score);
    }


    public void createTables() {
        final String[] queries = new String[]{
                "CREATE TABLE IF NOT EXISTS matches (challenge_id integer not null, game_id integer not null, match_id integer not null, p1_id integer not null, p2_id integer not null, status string, primary key(challenge_id, game_id, match_id) );",
                "CREATE TABLE IF NOT EXISTS tiles_placed (challenge_id integer not null, game_id integer not null, match_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, orientation integer not null, tile text not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "CREATE TABLE IF NOT EXISTS build_action (challenge_id integer not null, game_id integer not null, match_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, move_description text not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "CREATE TABLE IF NOT EXISTS invalid_moves (challenge_id integer not null, game_id integer not null, match_id integer not null, turn_number integer not null, p_id integer not null, message string not null, primary key(challenge_id, game_id, match_id, turn_number) );",
                "create table IF NOT EXISTS raw_requests ( time_stamp integer primary key, request text not null);",
                "create table if not exists overall_score (challenge_id integer, player_id integer not null, score integer not null);",
        };

        try {
            for ( String query : queries ) {
                Statement stmt = connection.createStatement();
                stmt.execute(query);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
