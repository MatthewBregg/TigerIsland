package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.player.PlayerID;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Settlement {
    private Map<Location,Piece> piecesInSettlement;
    private PlayerID playerID;

    public Settlement(Map<Location,Piece> piecesInSettlement, PlayerID playerID) {
        this.piecesInSettlement = piecesInSettlement;
        this.playerID = playerID;
    }

    public int settlementSize() {
        return piecesInSettlement.size();
    }

    public Set<Location> getConnectedLocations() {
        return piecesInSettlement.keySet();
    }

    public Piece getPieceAt(Location location) {
        return piecesInSettlement.get(location);
    }

    public boolean LocationOccupiedp(Location location) {
        return piecesInSettlement.containsKey(location);
    }

    public PlayerID getPlayerID() {
        return playerID;
    }
}