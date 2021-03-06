
/* This action woudl be for
1) Founding new settlment
2) Expanding settlement
 */

package tigerisland.build_moves.actions;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Totoro;
import tigerisland.piece.Villager;
import tigerisland.player.Player;

public class PlaceTotoroOnHexAction implements MakeBuildAction{

    private PieceBoard pieceBoard;

    public PlaceTotoroOnHexAction(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        Location hexLocation = buildActionData.getHexBuildLocation();
        player.removeTotoro();
        pieceBoard.addPiece( new Totoro(), hexLocation, player.getId());
    }
}
