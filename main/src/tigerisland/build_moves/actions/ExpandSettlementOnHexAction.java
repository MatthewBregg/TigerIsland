package tigerisland.build_moves.actions;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Villager;
import tigerisland.player.Player;

import java.util.Set;

public class ExpandSettlementOnHexAction implements MakeBuildAction {
    private PieceBoard pieceBoard;
    private SettlementExpansionUtility settlementExpansionUtility;

    public ExpandSettlementOnHexAction(PieceBoard pieceBoard, SettlementExpansionUtility settlementExpansionUtility) {
        this.pieceBoard = pieceBoard;
        this.settlementExpansionUtility = settlementExpansionUtility;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();

        int villagersNeeded = settlementExpansionUtility.getVillagersNeededToExpand(buildActionData);
        player.removeVillagers(villagersNeeded);

        Set<Location> expandableLocs = settlementExpansionUtility.getExpandableHexes(buildActionData);
        for ( Location expandableLoc : expandableLocs ) {
            pieceBoard.addPiece(new Villager(), expandableLoc, player.getId());
        }
    }
}
