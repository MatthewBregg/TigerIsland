package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import java.util.List;

public interface SettlementBoard {

    boolean isLocationOccupied(Location loc);

    Settlement getSettlement(Location location);

    boolean isLocationOccupied(Location loc, PlayerID playerID);

    Settlement getSettlement(Location location, PlayerID playerID);


}
