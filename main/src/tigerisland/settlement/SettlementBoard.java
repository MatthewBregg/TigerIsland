package tigerisland.settlement;

import tigerisland.board.Location;

import java.util.List;

public interface SettlementBoard {

    Settlement getSettlement(Location location);

    int settlementSize(Settlement settlement);

    List<Location> getConnectedLocations(Location location);
}
