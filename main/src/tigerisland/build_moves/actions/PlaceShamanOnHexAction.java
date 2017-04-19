package tigerisland.build_moves.actions;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Shaman;
import tigerisland.piece.Tiger;
import tigerisland.player.Player;

public class PlaceShamanOnHexAction implements MakeBuildAction {


    private PieceBoard pieceBoard;

    public PlaceShamanOnHexAction(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        Location hexLocation = buildActionData.getHexBuildLocation();
        player.removeShaman();
        pieceBoard.addPiece( new Shaman(), hexLocation, player.getId());
    }
}

