package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.Set;

public class ConsoleLogger implements DataLogger {

    private int challengeId;
    private int gameId;
    private int turnNumber = 0;

    public ConsoleLogger(int challengeId, int gameId) {
        this.challengeId = challengeId;
        this.gameId = gameId;
    }

    private String getMessageHeader() {
        return ("Logger: Game Id: " + gameId + " Challenge Id: " + challengeId + " Turn Number " + turnNumber + " ::");
    }


    @Override
    synchronized public void writeRawRequest(long timeStamp, String message) {

        System.out.println(getMessageHeader() + message);
    }

    @Override
    synchronized public void writePlacedTotoroMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + pid + "placed a totoro in location " + loc);
    }

    @Override
    synchronized public void writeFoundedSettlementMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + pid + "founded a settlement in location " + loc);
    }

    @Override
    synchronized public void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain) {
        System.out.println(getMessageHeader() + "Player " + pid + " expanded settlement at " + loc + " to cover terrain of " + terrain);
    }

    @Override
    synchronized public void writePlacedTigerMove(PlayerID pid, Location loc) {
        System.out.println(getMessageHeader() + "Player " + pid + "placed a tiger in location " + loc);
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
    synchronized public void writeGameEnded(PlayerID winner, PlayerID loser) {
        System.out.println(getMessageHeader() + "Winner " + winner + " Loser " + loser);
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

    synchronized public void newGame(int gameId, int challengeID) {
        System.out.println("Starting a new game!");
        this.gameId = gameId;
        this.challengeId = challengeID;
        turnNumber = 0;
    }
}
