package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;

public class SQLiteLogger implements DataLogger {

    private final Map<Integer, String> playersIdToUserName;
    private int challengeId;
    private char gameId;
    private int turnNumber = 0;
    private int matchId;
    private final Connection connection;
    private boolean hasError = false;


    public SQLiteLogger(int challengeId, char gameId, int matchId, Map<Integer, String> playersIdToUserName,
                                                                                Connection connection) {
        this.matchId = matchId;
        this.challengeId = challengeId;
        this.gameId = gameId;
        this.playersIdToUserName = playersIdToUserName;
        this.connection = connection;
    }
    public boolean hasErrored() {
        return hasError;
    }

    public void clearError() {
        hasError = false;
    }

    private void writeToBuildActions(PlayerID pid, Location loc, String move_description) {

        String query = "INSERT INTO build_action(challenge_id,game_id,match_id,turn_number,p_id,loc_x,loc_y,loc_z,move_description) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setString(2,String.valueOf(gameId));
            prstmnt.setInt(3,matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setString(5, getUserName(pid));
            prstmnt.setInt(6,loc.getX());
            prstmnt.setInt(7,loc.getY());
            prstmnt.setInt(8,loc.getZ());
            prstmnt.setString(9,move_description);
            synchronized (connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToBuildActions");
            System.err.println(sqlException);
            hasError = true;
        }

    }

    private int pidToInt(PlayerID pid) {
        return pid.getId();
    }

    private void writeToMatches(PlayerID p1, PlayerID p2, String status) {
        String query = "INSERT OR REPLACE INTO matches(challenge_id, game_id, match_id, p1_id, p2_id, status) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setString(2,String.valueOf(gameId));
            prstmnt.setInt(3,matchId);

            String player1UserName = getUserName(p1);
            String player2UserName = getUserName(p2);
            prstmnt.setString(4, player1UserName);
            prstmnt.setString(5, player2UserName);

            prstmnt.setString(6,status);
            synchronized (connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToMatches");
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private String getUserName(PlayerID p1) {
        String userName = this.playersIdToUserName.get(p1.getId());
        return  userName == null ? String.valueOf(p1.getId()) : userName;
    }

    private String getUserName(int p1) {
        String userName = this.playersIdToUserName.get(p1);
        return  userName == null ? String.valueOf(p1) : userName;
    }

    private void writeToOverallScore(int cid, int p_id, int score) {
        String query = "INSERT OR REPLACE INTO overall_score(challenge_id,player_id,score) VALUES(?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, cid);
            prstmnt.setString(2, getUserName(p_id));
            prstmnt.setInt(3, score);
            synchronized ( connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToOverallScore");
            System.err.println(sqlException);
            hasError = true;
        }
    }

    public void writeToPlayerPieceCount(PlayerID pID, int villagerCount){
        String query = "INSERT OR REPLACE INTO player_piece_count(challenge_id, player_id, match_id, game_id, villager_count) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setString(2, getUserName(pID));
            prstmnt.setInt(3, matchId);
            prstmnt.setString(4, String.valueOf(gameId));
            prstmnt.setInt(5, villagerCount);
            synchronized ( connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void writeToChallengeScore(int cid, int pid, int score){
        String query = "INSERT OR REPLACE INTO challenge_score(challenge_id, player_id, score) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, cid);
            preparedStatement.setInt(2, pid);
            preparedStatement.setInt(3, score);
        }catch(SQLException sqlException){
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
            synchronized (connection) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToRawRequest");
            System.err.println(sqlException);
            hasError = true;
        }
    }

    private void writeToTilesPlaced(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        String query = "INSERT INTO tiles_placed(challenge_id,game_id,match_id,turn_number,p_id,loc_x,loc_y,loc_z,orientation,tile) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setString(2,String.valueOf(gameId));
            prstmnt.setInt(3,matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setString(5, getUserName(pid));
            prstmnt.setInt(6,loc.getX());
            prstmnt.setInt(7,loc.getY());
            prstmnt.setInt(8,loc.getZ());
            prstmnt.setInt(9,orientation.getAngle());
            prstmnt.setString(10,tileTerrains);
            synchronized (connection) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToTilesPlaced");
            System.err.println(sqlException);
            hasError = true;
        }
    }

    @Override
    public void writeRawRequest(long timeStamp, String message) {
        writeToRawRequest(timeStamp, message);
    }

    @Override
    public void writeToTournamentScore(PlayerID pid, int score) {
        String query = "INSERT OR REPLACE INTO tournament_score(player_id, score) VALUES(?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setString(1, getUserName(pid));
            prstmnt.setInt(2, score);
            synchronized ( connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToTournamentScore ");
            System.err.println(sqlException);
            hasError = true;
        }
    }

    @Override
    public void writeToGameTurnScore(PlayerID pId, int moveId, int score) {
        String query = "INSERT OR REPLACE INTO game_turn_score(challenge_id, player_id, match_id, game_id, move_id, score) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement prstmnt = connection.prepareStatement(query);
            prstmnt.setInt(1, challengeId);
            prstmnt.setString(2, getUserName(pId));
            prstmnt.setInt(3, matchId);
            prstmnt.setString(4, String.valueOf(gameId));
            prstmnt.setInt(5,moveId);
            prstmnt.setInt(6, score);
            synchronized ( connection ) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.err.println("Happening at writeToGameTurnScore");
            System.err.println(sqlException);
            hasError = true;
        }
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
            prstmnt.setString(2,String.valueOf(gameId));
            prstmnt.setInt(3, matchId);
            prstmnt.setInt(4, turnNumber);
            prstmnt.setString(5, getUserName(pid));
            prstmnt.setString(6,message);
            synchronized (connection) {
                prstmnt.executeUpdate();
            }
        } catch (SQLException sqlException) {
                System.err.println("Happening at writeToInvalidMoves");
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
    synchronized public void nextTurn() {
        ++turnNumber;
    }

    public void newGame(char gameId, int challengeID) {
        this.gameId = gameId;
        this.challengeId = challengeID;
        turnNumber = 0;
    }

    public void setPlayerScore(int cid, PlayerID pid, int score) {
        writeToOverallScore(cid, pid.getId(),score);
    }
}
