package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import java.util.List;

public interface SettlementBoard {

    boolean LocationOccupiedp(Location loc, PlayerID playerID);
    Settlement getSettlement(Location location, PlayerID playerID);

}
