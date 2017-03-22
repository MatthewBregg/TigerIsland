package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;
import tigerisland.player.PlayerID;

import java.util.HashMap;
import java.util.Map;

/*
    Dynimically find out about settlements using the piece board.
    To get settlements per player, will need one board per player.
 */
public class LazySettlementBoard implements SettlementBoard {
    PieceBoard pieceBoard;
    public LazySettlementBoard(PieceBoard peiceBoard) {
        this.pieceBoard = peiceBoard;
    }
    @Override
    public Settlement getSettlement(Location location) {
        if (!this.LocationOccupiedp(location)) {
            throw new IllegalArgumentException("No settlement at this location!");
        } else {
            Map<Location, Piece> pieceMap = new HashMap<Location,Piece>();
            PlayerID playerID = pieceBoard.getPlayer(location);
            generatePieceMapAtLocation(location,pieceMap,playerID);
            return new Settlement(pieceMap,playerID);
        }
    }

    private void generatePieceMapAtLocation(Location location, Map<Location,Piece> pieceMap, PlayerID playerID) {
        if ( pieceMap.containsKey(location)) { return; }
        assert( playerID.equals(pieceBoard.getPlayer(location)));
        pieceMap.put(location, pieceBoard.getPiece(location));

        for ( Location adjLoc : location.getSurroundingLocations()) {
            if (pieceBoard.LocationOccupiedp(adjLoc) && playerID.equals(pieceBoard.getPlayer(adjLoc))) {
                generatePieceMapAtLocation(adjLoc, pieceMap,playerID);
            }
        }
    }

    @Override
    public boolean LocationOccupiedp(Location loc) {
        return ( pieceBoard.LocationOccupiedp(loc));
    }
}
