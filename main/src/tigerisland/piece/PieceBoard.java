package tigerisland.piece;

import tigerisland.board.Location;

public interface PieceBoard {
    Piece getPiece(Location location);
    boolean LocationOccupiedp(Location location);
}
