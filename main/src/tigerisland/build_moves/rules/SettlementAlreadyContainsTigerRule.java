package tigerisland.build_moves.rules;

import tigerisland.build_moves.NearbySettlementsUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.TigerCounter;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;


public class SettlementAlreadyContainsTigerRule implements BuildActionRule {

    private PieceBoard pieceBoard;
    private String TIGER_BUILD_HEX_ERROR = "Tiger build hex must be adjacent to a settlement that doesn't contain a tiger";

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, TIGER_BUILD_HEX_ERROR);
    SettlementBoard settlementBoard = null;
    private SettlementLocationAdjacentToHexLocationRule adjacent = null;

    public SettlementAlreadyContainsTigerRule(SettlementBoard board){this.settlementBoard=board;}

    public BuildActionResult applyRule(BuildActionData buildActionData){
        return adjacentSettlementsDoNotAlreadyContainATiger(buildActionData) ? successfulResult : failedResult;
    }


    private boolean adjacentSettlementsDoNotAlreadyContainATiger(BuildActionData buildData){

        NearbySettlementsUtility adjacentFinder = new NearbySettlementsUtility(buildData, settlementBoard);

        List<Settlement> possibleBuildSettlements = adjacentFinder.getPossibleSettlementsForBuild();

        List<Settlement> buildSettlements = new ArrayList<>();


        for (Settlement settlement : possibleBuildSettlements) {
           TigerCounter tc = new TigerCounter();
           settlement.acceptVisitor(tc);
           if (tc.getCount()<1) {
               buildSettlements.add(settlement);
           }
        }

        if (buildSettlements.size()==0) {
            return false;
        }

        return true;

    }
}
