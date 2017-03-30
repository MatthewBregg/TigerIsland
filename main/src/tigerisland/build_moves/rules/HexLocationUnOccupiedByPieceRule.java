package tigerisland.build_moves.rules;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.PieceBoard;

public class HexLocationUnOccupiedByPieceRule implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Hex Location is Occupied by Piece!");
    private PieceBoard pieceBoard;

    public HexLocationUnOccupiedByPieceRule(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
       if ( pieceBoard.isLocationOccupied(buildActionData.getHexBuildLocation())) {
           return failedResult;
       } else {
           return successfulResult;
       }
    }
}
