package tigerisland.build_moves.rules;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.SettlementBoard;

public class TotoroBuildHexAdjacentToSettlementSizeFiveRule implements BuildActionRule{


    private PieceBoard pieceBoard;
    private final String TOTORO_BULD_HEX_ADJACENT_SIZE_ERROR = "Totoro build hex must be adjacent to a settlment of size >=5";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, TOTORO_BULD_HEX_ADJACENT_SIZE_ERROR);
    SettlementBoard settlementBoard = null;


    public TotoroBuildHexAdjacentToSettlementSizeFiveRule(SettlementBoard board){
        this.settlementBoard = board;
    }


    public BuildActionResult applyRule(BuildActionData buildActionData){
        return isTotoroBuildHexAdjacentToSettlementSizeFiveRule(buildActionData) ? successfulResult : failedResult;
    }


    private boolean isTotoroBuildHexAdjacentToSettlementSizeFiveRule(BuildActionData buildData){
        Location location = buildData.getHexBuildLocation();
        return false;


    }
}
