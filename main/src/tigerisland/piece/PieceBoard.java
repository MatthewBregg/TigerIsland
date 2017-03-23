package tigerisland.piece;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

public interface PieceBoard {
    Piece getPiece(Location location);
    boolean LocationOccupiedp(Location location);
    Piece getPiece(Location location, PlayerID playerID);
    boolean LocationOccupiedp(Location location, PlayerID playerID);

    PlayerID getPlayer(Location location);
}
