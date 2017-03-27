package tigerisland.build.rules;

import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.piece.PieceBoard;

public class EmptyHexRule implements BuildActionRule{

    private PieceBoard pieceBoard;
    private final String EMPTY_HEX_ERROR_MESSAGE = "Hex is NOT empty";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, EMPTY_HEX_ERROR_MESSAGE);

    public EmptyHexRule(PieceBoard pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        return isBoardHexEmptyAtLocation(buildActionData.getHexLocation()) ? successfulResult : failedResult;
    }

    private boolean isBoardHexEmptyAtLocation(Location hexLocation) {
        return pieceBoard.isLocationOccupied(hexLocation);
    }

}
