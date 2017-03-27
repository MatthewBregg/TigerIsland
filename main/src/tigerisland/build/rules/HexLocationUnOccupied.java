package tigerisland.build.rules;

import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.piece.PieceBoard;

public class HexLocationUnOccupied implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Hex Location is Occupied!");
    private PieceBoard pieceBoard;

    public HexLocationUnOccupied(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
       if ( pieceBoard.isLocationOccupied(buildActionData.getHexLocation())) {
           return failedResult;
       } else {
           return successfulResult;
       }
    }
}
