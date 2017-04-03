package tigerisland.build_moves.rules;

import tigerisland.build_moves.NearbySettlementsUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.TotoroCounter;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;



public class SettlementAlreadyContainsTotoroRule implements BuildActionRule {

    private String TOTORO_BUILD_HEX_ERROR = "Totoro build hex must be adjacent to a settlement that doesn't contain a totoro";

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, TOTORO_BUILD_HEX_ERROR);
    SettlementBoard settlementBoard = null;
    private SettlementLocationAdjacentToHexLocationRule adjacent = null;

    public SettlementAlreadyContainsTotoroRule(SettlementBoard board){this.settlementBoard=board;}

    public BuildActionResult applyRule(BuildActionData buildActionData){
        return adjacentSettlementsDoNotAlreadyContainATotoro(buildActionData) ? successfulResult : failedResult;
    }


    private boolean adjacentSettlementsDoNotAlreadyContainATotoro(BuildActionData buildData){

        NearbySettlementsUtility adjacentFinder = new NearbySettlementsUtility(buildData, settlementBoard);

        List<Settlement> possibleBuildSettlements = adjacentFinder.getPossibleSettlementsForBuild();

        List<Settlement> buildSettlements = new ArrayList<>();

        for (Settlement settlement : possibleBuildSettlements){
            TotoroCounter tc = new TotoroCounter();
            settlement.acceptVisitor(tc);
            if (tc.getCount()<1)
                buildSettlements.add(settlement);
        }

        if (buildSettlements.size()==0)
            return false;

        return true;

    }


}


