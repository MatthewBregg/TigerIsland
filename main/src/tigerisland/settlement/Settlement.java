package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Settlement {
    private Map<Location,Piece> piecesInSettlement;

    public Settlement(Map<Location,Piece> piecesInSettlement) {
        this.piecesInSettlement = piecesInSettlement;
    }

    public int settlementSize() {
        return piecesInSettlement.size();
    }

    public Set<Location> getConnectedLocations(Settlement settlement) {
        return piecesInSettlement.keySet();
    }

    public Piece getPieceAt(Location location) {
        return piecesInSettlement.get(location);
    }

    public boolean LocationOccupiedp(Location location) {
        return true;
    }
}
