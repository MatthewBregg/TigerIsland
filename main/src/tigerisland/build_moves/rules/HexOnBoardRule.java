package tigerisland.build_moves.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.hex.Hex;
import tigerisland.hex.NullHex;

public class HexOnBoardRule implements BuildActionRule{

    private Board board;
    private final String EMPTY_HEX_ERROR_MESSAGE = "Hex is NOT empty";
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
       Hex hexBoard = board.getHex(hexLocation);
       return !(hexBoard instanceof NullHex);
    }

}
