package tigerisland.build.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.hex.Hex;
import tigerisland.hex.NullHex;

public class HexOnBoardRule implements BuildActionRule{

    private Board board;
    private final String EMPTY_HEX_ERROR_MESSAGE = "Hex is not on board";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, EMPTY_HEX_ERROR_MESSAGE);

    public HexOnBoardRule(Board board) {
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
