package tigerisland.settlement;

import tigerisland.board.Location;

import java.util.List;

public interface SettlementBoard {

    Settlement getSettlement(Location location);
    boolean LocationOccupiedp(Location loc);

}
