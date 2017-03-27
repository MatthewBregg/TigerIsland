package tigerisland.build_moves.actions;







import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
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
        Location hexLocation = buildActionData.getHexBuildLocation();
        player.removeVillager();
        pieceBoard.addPiece( new Villager(), hexLocation, player.getId());
    }
}
