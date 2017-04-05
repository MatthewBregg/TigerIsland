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
        challengeId = challengeId;
        gameId = gameId;
    }

    private void printMessageHeader() {
        System.out.print("Game Id: " + gameId + " Challenge Id: " + challengeId + " Turn Number " + turnNumber + " ::");
    }


    @Override
    public void writeRawRequest(long timeStamp, String message) {
        printMessageHeader();
        System.out.println(message);
    }

    @Override
    public void writePlacedTotoro(PlayerID pid, Location loc) {
        printMessageHeader();
        System.out.println("Player " + pid + "placed a totoro in location " + loc);
    }

    @Override
    public void writeFoundedSettlement(PlayerID pid, Location loc) {
        printMessageHeader();
        System.out.println("Player " + pid + "founded a settlement in location " + loc);
    }

    @Override
    public void writeExpandedSettlement(PlayerID pid, Location loc, String terrain) {
        printMessageHeader();
        System.out.println("Player " + pid + " expanded settlement at " + loc + " to cover terrain of " + terrain);
    }

    @Override
    public void writePlacedTiger(PlayerID pid, Location loc) {
        printMessageHeader();
        System.out.println("Player " + pid + "placed a tiger in location " + loc);
    }

    @Override
    public void writePlacedTile(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        printMessageHeader();
        System.out.println("Player " + pid + "placed a tile in location " + loc +
                " with the follow terrain " + tileTerrains + " at orientation " + orientation);
    }

    @Override
    public void writeInvalidMove(PlayerID pid, String message) {
        this.writeMovedWasInvalid();
    }


    private void writeMovedWasInvalid() {
        printMessageHeader();
        System.out.println("Error, move was invalid!!");
    }

    @Override
    public void writeGameEnded(PlayerID winner, PlayerID loser) {
        printMessageHeader();
        System.out.println("Winner " + winner + " Loser " + loser);
    }

    @Override
    public void writeGameStarted(PlayerID p1, PlayerID p2) {
        printMessageHeader();
        System.out.println("p1 " + p1 + " p2 " + p2);
    }

    @Override
    public void nextTurn() {
        printMessageHeader();
        System.out.println(" Turn has ended");
        turnNumber++;
    }

    @Override
    public void newGame(int gameId, int challengeID) {
        System.out.println("Starting a new game!");
        this.gameId = gameId;
        this.challengeId = challengeID;
        turnNumber = 0;
    }
}
