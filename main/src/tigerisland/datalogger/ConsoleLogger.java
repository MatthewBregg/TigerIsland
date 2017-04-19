package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.Map;

public class ConsoleLogger implements DataLogger {

    private final Map<Integer, String> playersIdToUsername;
    private int challengeId;
    private char gameId;
    private int turnNumber = 0;
    private int matchId = 0;

    public ConsoleLogger(int challengeId, char gameId, int matchId, Map<Integer, String> playersIdToUsername) {
        this.challengeId = challengeId;
        this.matchId = matchId;
        this.gameId = gameId;
        this.playersIdToUsername = playersIdToUsername;
    }

    private String getMessageHeader() {
        return ("Logger: Game Id: " + gameId + " Challenge Id: " + challengeId + " Match ID : " + matchId + " Turn Number " + turnNumber + " ::");
    }
    @Override
    synchronized public void writeToChallenges(PlayerID p1, PlayerID p2, int p1score, int p2score) {
//        System.out.println("");

    }


    @Override
    synchronized public void writeRawRequest(long timeStamp, String message) {
        System.out.println(getMessageHeader() + message);
    }

    @Override
    public void writeToTournamentScore(PlayerID pid, int score) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pid) + " tournament score: " + score);
    }

    @Override
    public void writeToGameTurnScore(PlayerID pId, int moveId, int score) {}

    @Override
    public void writeToPlayerPieceCount(PlayerID pID, int totoroCount) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pID) + " placed  " + totoroCount);
    }

    @Override
    synchronized public void writePlacedTotoroMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pid) + "placed a totoro in location " + loc);
    }

    @Override
    synchronized public void writeFoundedSettlementMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pid) + "founded a settlement in location " + loc);
    }

    @Override
    synchronized public void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pid) + " expanded settlement at " + loc + " to cover terrain of " + terrain);
    }

    @Override
    synchronized public void writePlacedTigerMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + getUserName(pid) + "placed a tiger in location " + loc);
    }

    @Override
    synchronized public void writePlacedTileMove(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        System.out.println(getMessageHeader() +  " placed a tile in location " + loc +
                " with the follow terrain " + tileTerrains + " at orientation " + orientation);
    }

    @Override
    synchronized public void writeInvalidMoveAttempted(PlayerID pid, String message) {
        this.writeMovedWasInvalid(message);
    }

    @Override
    public void writeMoveResult(String message) {
        System.out.println(getMessageHeader() + message);
    }

    private void writeMovedWasInvalid(String message) {
        System.out.println(getMessageHeader() + "Error, move was invalid!! " + message);
    }

    @Override
    synchronized public void writeGameEnded(PlayerID winner, PlayerID loser,  String matchEndCondition) {
        System.out.println(getMessageHeader() +  matchEndCondition +  " Winner " + winner + " Loser " + loser);
    }

    @Override
    synchronized public void writeGameStarted(PlayerID p1, PlayerID p2) {
        System.out.println(getMessageHeader() + "p1 " + p1 + " p2 " + p2);
    }

    @Override
    synchronized public void nextTurn() {
        System.out.println(getMessageHeader() + " Turn has ended");
        turnNumber++;
    }

    synchronized public void newGame(char gameId, int challengeID) {
        System.out.println("Starting a new game!");
        this.gameId = gameId;
        this.challengeId = challengeID;
        turnNumber = 0;
    }

    private String getUserName(PlayerID p1) {
        String userName = this.playersIdToUsername.get(p1.getId());
        return  userName == null ? String.valueOf(p1.getId()) : userName;
    }

}
