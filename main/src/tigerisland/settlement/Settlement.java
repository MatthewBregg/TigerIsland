package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Settlement {

    public Settlement(Map<Location,Piece> piecesInSettlement) {
        this.piecesInSettlement = piecesInSettlement;
    }

    Map<Location,Piece> piecesInSettlement;
    public int settlementSize() {
        return piecesInSettlement.size();
    }

    public Set<Location> getConnectedLocations(Settlement settlement) {
        return piecesInSettlement.keySet();
    }
}
