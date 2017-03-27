package tigerisland.build.rules;

import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.build.SettlementExpansionUtility;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.Set;

public class SettlementLocationAdjacentToHexLocation implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Hex Location is not adjacent to settlement!!");

    private SettlementBoard settlementBoard;

    public SettlementLocationAdjacentToHexLocation(SettlementBoard settlementBoard) {
        this.settlementBoard = settlementBoard;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Settlement s = settlementBoard.getSettlement(buildActionData.getSettlementLocation());
        Set<Location> settlementLocations = s.getConnectedLocations();
        for ( Location settlementLocation : settlementLocations ) {
            if ( settlementLocation.getSurroundingLocations().contains(buildActionData.getHexLocation())) {
                return successfulResult;
            }
        }
        return failedResult;
    }
}
