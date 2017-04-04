package tigerisland.build_moves.actions;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Tiger;
import tigerisland.player.Player;

public class PlaceTigerOnHexAction implements MakeBuildAction {


    private PieceBoard pieceBoard;

    public PlaceTigerOnHexAction(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        Location hexLocation = buildActionData.getHexBuildLocation();
        player.removeTiger();
        pieceBoard.addPiece( new Tiger(), hexLocation, player.getId());
    }
}

