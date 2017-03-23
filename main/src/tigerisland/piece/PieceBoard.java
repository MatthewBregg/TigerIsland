package tigerisland.piece;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

public interface PieceBoard {
    Piece getPiece(Location location);
    boolean LocationOccupiedp(Location location);

    PlayerID getPlayer(Location location);
}
