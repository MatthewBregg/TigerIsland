package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import java.util.List;

public interface SettlementBoard {

    Settlement getSettlement(Location location);
    boolean LocationOccupiedp(Location loc);

    boolean LocationOccupiedp(Location loc, PlayerID playerID);
    Settlement getSettlement(Location location, PlayerID playerID);

}
