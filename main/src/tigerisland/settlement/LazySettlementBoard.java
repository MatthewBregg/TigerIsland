package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;

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
            generatePieceMapAtLocation(location,pieceMap);
            return new Settlement((pieceMap));
        }
    }

    private void generatePieceMapAtLocation(Location location, Map<Location,Piece> pieceMap) {
        if ( pieceMap.containsKey(location)) { return; }
        pieceMap.put(location,pieceBoard.getPiece(location));
        for ( Location adjLoc : location.getSurroundingLocations()) {
            if (pieceBoard.LocationOccupiedp(adjLoc)) {
                generatePieceMapAtLocation(adjLoc, pieceMap);
            }
        }
    }

    @Override
    public boolean LocationOccupiedp(Location loc) {
        return ( pieceBoard.LocationOccupiedp(loc));
    }
}
