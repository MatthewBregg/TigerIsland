package tigerisland;

import tigerisland.board.Location;
import tigerisland.datalogger.DataLogger;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

public class TestLogger implements DataLogger {
    // A logger that does nothing for testing.
    @Override
    public void writeRawRequest(long timeStamp, String message) {

    }

    @Override
    public void writePlacedTotoroMove(PlayerID pid, Location loc) {

    }

    @Override
    public void writeFoundedSettlementMove(PlayerID pid, Location loc) {

    }

    @Override
    public void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain) {

    }

    @Override
    public void writePlacedTigerMove(PlayerID pid, Location loc) {

    }

    @Override
    public void writePlacedTileMove(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {

    }

    @Override
    public void writeInvalidMoveAttempted(PlayerID pid, String message) {

    }

    @Override
    public void writeGameEnded(PlayerID winner, PlayerID loser) {

    }

    @Override
    public void writeGameStarted(PlayerID p1, PlayerID p2) {

    }

    @Override
    public void nextTurn() {

    }
    
    public void newGame(int gameId, int challengeID) {

    }
}
