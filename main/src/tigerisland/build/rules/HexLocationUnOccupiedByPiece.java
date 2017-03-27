package tigerisland.build.rules;

import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.piece.PieceBoard;

public class HexLocationUnOccupiedByPiece implements BuildActionRule {

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, "Hex Location is Occupied by Piece!");
    private PieceBoard pieceBoard;

    public HexLocationUnOccupiedByPiece(PieceBoard pieceBoard) {
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
