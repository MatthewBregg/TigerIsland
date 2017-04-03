package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.Set;

public interface DataLogger {
    void writeRawRequest(long timeStamp, String message);
    void writePlacedTotoro(PlayerID pid, Location loc);
    void writeFoundedSettlement(PlayerID pid, Location loc);
    void writeExpandedSettlement(PlayerID pid, Location loc, String terrain);
    void writePlacedTiger(PlayerID pid, Location loc);
    void writePlacedTile(PlayerID pid, Location loc, Orientation orientation, String tileTerrains);
    void writeInvalidMove(PlayerID pid, String message);

    void writeGameEnded(PlayerID winner, PlayerID loser);
    void writeGameStarted(PlayerID p1, PlayerID p2);

    void nextTurn();
}
