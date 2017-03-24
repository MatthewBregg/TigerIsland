package tigerisland.piece;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import java.util.HashMap;
import java.util.Map;

public class PieceBoardImpl implements PieceBoard {
    private class PiecePlayerAssocation {
        final public Piece piece;
        final public PlayerID playerID;
        public PiecePlayerAssocation(Piece piece, PlayerID playerID) {
            this.piece = piece;
            this.playerID = playerID;
        }
    }

    private Map<Location,PiecePlayerAssocation> pieceMap = new HashMap<Location,PiecePlayerAssocation>();

    @Override
    public Piece getPiece(Location location) {
        PiecePlayerAssocation ppa = pieceMap.get(location);
        return (ppa == null) ? new NullPiece() : ppa.piece;
    }

    @Override
    public boolean LocationOccupiedp(Location location) {
        return pieceMap.get(location) != null;
    }

    @Override
    public Piece getPiece(Location location, PlayerID playerID) {
        PiecePlayerAssocation ppa = pieceMap.get(location);
        return (ppa != null && playerID.equals(ppa.playerID)) ? ppa.piece : new NullPiece();
    }

    @Override
    public boolean LocationOccupiedp(Location location, PlayerID playerID) {
        PiecePlayerAssocation ppa = pieceMap.get(location);
        return (ppa != null && playerID.equals(ppa.playerID));
    }

    @Override
    public PlayerID getPlayer(Location location) {
        PiecePlayerAssocation ppa = pieceMap.get(location);
        return (ppa != null ) ? ppa.playerID : null;
    }


    public void addPiece(Piece p, Location loc, PlayerID pID) {
        pieceMap.put(loc, new PiecePlayerAssocation(p,pID));
    }

    public void clearLocation(Location loc) {
        pieceMap.remove(loc);
    }
}
