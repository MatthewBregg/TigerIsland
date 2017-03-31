package tigerisland.build_moves.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;

public class BuildLocationMustBeOnBoardRule implements BuildActionRule{

    private Board board;
    private final String EMPTY_HEX_ERROR_MESSAGE = "Hex does not exist on board.";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, EMPTY_HEX_ERROR_MESSAGE);

    public BuildLocationMustBeOnBoardRule(Board board) {
        this.board = board;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        return isThereAHexAtLocation(buildActionData.getHexBuildLocation()) ? successfulResult : failedResult;
    }

    private boolean isThereAHexAtLocation(Location hexLocation) {
        return board.isLocationUsed(hexLocation);
    }

}
