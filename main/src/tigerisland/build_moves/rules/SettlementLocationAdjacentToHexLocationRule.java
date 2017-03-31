package tigerisland.build_moves.rules;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.Set;

public class SettlementLocationAdjacentToHexLocationRule implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Hex Location is not adjacent to settlement!!");

    private SettlementBoard settlementBoard;

    public SettlementLocationAdjacentToHexLocationRule(SettlementBoard settlementBoard) {
        this.settlementBoard = settlementBoard;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Settlement s = settlementBoard.getSettlement(buildActionData.getSettlementToExpandFromLocation());
        Set<Location> settlementLocations = s.getConnectedLocations();
        for ( Location settlementLocation : settlementLocations ) {
            if ( settlementLocation.getSurroundingLocations().contains(buildActionData.getHexBuildLocation())) {
                return successfulResult;
            }
        }
        return failedResult;
    }
}
