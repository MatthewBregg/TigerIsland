package tigerisland.piece;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import java.util.HashMap;
import java.util.Map;

public class PieceBoardImpl implements PieceBoard {
    private class PiecePlayerAssociation {
        final public Piece piece;
        final public PlayerID playerID;

        public PiecePlayerAssociation(Piece piece, PlayerID playerID) {
            this.piece = piece;
            this.playerID = playerID;
        }
    }

    private Map<Location,PiecePlayerAssociation> pieceMap = new HashMap<>();

    @Override
    public Piece getPiece(Location location) {
        PiecePlayerAssociation ppa = pieceMap.get(location);
        return (ppa == null) ? new NullPiece() : ppa.piece;
    }

    @Override
    public boolean isLocationOccupied(Location location) {
        return pieceMap.get(location) != null;
    }

    @Override
    public Piece getPiece(Location location, PlayerID playerID) {
        PiecePlayerAssociation ppa = pieceMap.get(location);
        return (ppa != null && playerID.equals(ppa.playerID)) ? ppa.piece : new NullPiece();
    }

    @Override
    public boolean isLocationOccupied(Location location, PlayerID playerID) {
        PiecePlayerAssociation ppa = pieceMap.get(location);
        return (ppa != null && playerID.equals(ppa.playerID));
    }

    @Override
    public PlayerID getPlayer(Location location) {
        PiecePlayerAssociation ppa = pieceMap.get(location);
        return (ppa != null ) ? ppa.playerID : null;
    }

    @Override
    public void removePiece(Location location) {
        pieceMap.remove(location);
    }

    @Override
    public void addPiece(Piece p, Location loc, PlayerID pID) {
        pieceMap.put(loc, new PiecePlayerAssociation(p,pID));
    }

    public void clearLocation(Location loc) {
        pieceMap.remove(loc);
    }
}
