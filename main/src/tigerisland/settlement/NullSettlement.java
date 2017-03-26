package tigerisland.settlement;

import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.player.PlayerID;

import java.util.Map;

public class NullSettlement extends Settlement {



    public NullSettlement(Map<Location, Piece> piecesInSettlement, PlayerID playerID) {
        super(piecesInSettlement, playerID);
    }
}
