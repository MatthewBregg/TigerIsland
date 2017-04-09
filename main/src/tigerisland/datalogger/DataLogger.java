package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.Set;

public interface DataLogger {
    void writeRawRequest(long timeStamp, String message);
    void writePlacedTotoroMove(PlayerID pid, Location loc);
    void writeFoundedSettlementMove(PlayerID pid, Location loc);
    void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain);
    void writePlacedTigerMove(PlayerID pid, Location loc);
    void writePlacedTileMove(PlayerID pid, Location loc, Orientation orientation, String tileTerrains);
    void writeInvalidMoveAttempted(PlayerID pid, String message);

    void writeGameEnded(PlayerID winner, PlayerID loser);
    void writeGameStarted(PlayerID p1, PlayerID p2);

    void nextTurn();

    void newGame(int gameId, int challengeID);
}
