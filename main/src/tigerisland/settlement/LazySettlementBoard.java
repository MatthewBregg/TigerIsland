package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;
import tigerisland.player.PlayerID;

import java.util.HashMap;
import java.util.Map;

/**
    Dynimically find out about settlements using the piece board.
    To get settlements per player, will need one board per player.
 */
public class LazySettlementBoard implements SettlementBoard {
    PieceBoard pieceBoard;
    public LazySettlementBoard(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public Settlement getSettlement(Location location) {
        if (!this.isLocationOccupied(location)) {
            return CreateEmptySettlement(null);
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
            if (pieceBoard.isLocationOccupied(adjLoc) && playerID.equals(pieceBoard.getPlayer(adjLoc))) {
                generatePieceMapAtLocation(adjLoc, pieceMap,playerID);
            }
        }
    }

    @Override
    public boolean isLocationOccupied(Location loc) {
        return ( pieceBoard.isLocationOccupied(loc));
    }

    /**
     *
     * @param loc The location to check
     * @param playerID The player id to look for
     * @return Boolean to indicate if there is a settlement by player, playerID, in location, loc.
     */
    @Override
    public boolean isLocationOccupied(Location loc, PlayerID playerID) {
        return (pieceBoard.isLocationOccupied(loc)) && playerID.equals(pieceBoard.getPlayer(loc));
    }

    @Override
    public Settlement getSettlement(Location location, PlayerID playerID) {
        Settlement s = this.getSettlement(location);
        if ( playerID.equals(s.getPlayerID())) {
            return s;

        } else {
            return this.CreateEmptySettlement(playerID);
        }

    }

    private Settlement CreateEmptySettlement(PlayerID pID) {
        return new Settlement(new HashMap<>(), pID);
    }
}
