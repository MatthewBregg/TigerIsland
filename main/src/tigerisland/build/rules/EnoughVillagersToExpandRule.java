package tigerisland.build.rules;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.build.SettlementExpansionUtility;
import tigerisland.piece.PieceBoard;
import tigerisland.player.Player;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Terrain;

import java.util.HashSet;
import java.util.Set;

public class EnoughVillagersToExpandRule implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Not enough villagers!!");

    private SettlementExpansionUtility settlementExpansionUtility;

    public EnoughVillagersToExpandRule(SettlementExpansionUtility settlementExpansionUtility) {
        this.settlementExpansionUtility  = settlementExpansionUtility;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        // We now have all the hexes we can expand to, and they all should be on the map.
        int villagersNeeded = 0;
        for (Location expandableLoc : settlementExpansionUtility.getSettlementsToExpandto(buildActionData)) {
            villagersNeeded+= settlementExpansionUtility.getHexLevel(expandableLoc);
        }

        if ( villagersNeeded <= buildActionData.getPlayer().getVillagerCount() ) {
            return successfulResult;
        } else {
            return failedResult;
        }
    }
}
