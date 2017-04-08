package tigerisland.piece;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;

public interface PieceBoard {

    Piece getPiece(Location location);

    boolean isLocationOccupied(Location location);

    Piece getPiece(Location location, PlayerID playerID);

    void addPiece(Piece p, Location loc, PlayerID pID);

    boolean isLocationOccupied(Location location, PlayerID playerID);

    PlayerID getPlayer(Location location);

    void removePiece(Location location);
}
