package tigerisland.build_moves.rules;

import tigerisland.build_moves.NearbySettlementsUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.List;

public class TotoroBuildHexAdjacentToSettlementSizeFiveRule implements BuildActionRule{


    private PieceBoard pieceBoard;
    private String TOTORO_BULD_HEX_ADJACENT_SIZE_ERROR = "Totoro build hex must be adjacent to a settlment of size >=5";

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, TOTORO_BULD_HEX_ADJACENT_SIZE_ERROR);
    SettlementBoard settlementBoard = null;
    private SettlementLocationAdjacentToHexLocationRule adjacent = null;


    public TotoroBuildHexAdjacentToSettlementSizeFiveRule(SettlementBoard board){
       this.settlementBoard = board;
    }


    public BuildActionResult applyRule(BuildActionData buildActionData){
        return isTotoroBuildHexAdjacentToSettlementSizeFiveRule(buildActionData) ? successfulResult : failedResult;
    }

    private boolean isTotoroBuildHexAdjacentToSettlementSizeFiveRule(BuildActionData buildData){
        NearbySettlementsUtility adjacentFinder = new NearbySettlementsUtility(buildData, settlementBoard);

        List<Settlement> adjacentSettlements = adjacentFinder.getPossibleSettlementsForBuild();

        if (adjacentSettlements.size()==0)
            return false;

        for (Settlement settlement :adjacentSettlements){
            if (settlement.settlementSize()<5)
                return false;
        }
        return true;
    }
}
