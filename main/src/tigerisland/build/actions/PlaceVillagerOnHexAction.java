package tigerisland.build.actions;

import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Villager;
import tigerisland.player.Player;

public class PlaceVillagerOnHexAction implements MakeBuildAction{

    private PieceBoard pieceBoard;

    public PlaceVillagerOnHexAction(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        Location hexLocation = buildActionData.getHexLocation();
        player.removeVillager();
        pieceBoard.addPiece( new Villager(), hexLocation, player.getId());
    }
}
