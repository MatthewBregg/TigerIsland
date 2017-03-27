package tigerisland.build.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.hex.Hex;

public class HexLevelOneRule implements BuildActionRule {

    private Board board;
    private final String LEVEL_ONE_HEX_ERROR_MESSAGE = "Hex is NOT a level one hex.";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, LEVEL_ONE_HEX_ERROR_MESSAGE);

    public HexLevelOneRule(Board board) {
       this.board = board;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        return isHexAtLocationLevelOne(buildActionData.getHexLocation()) ? successfulResult : failedResult;
    }

    private boolean isHexAtLocationLevelOne(Location hexLocation) {
       Hex boardHex = board.getHex(hexLocation);
       return boardHex.getLevel() == 1;
    }
}
