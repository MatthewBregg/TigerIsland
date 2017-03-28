package tigerisland.build_moves.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.hex.Hex;

public class SettlementMustBeFoundedHexLevelOneRule implements BuildActionRule {

    private Board board;
    private final String LEVEL_ONE_HEX_ERROR_MESSAGE = "Hex is NOT a level one hex.";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, LEVEL_ONE_HEX_ERROR_MESSAGE);

    public SettlementMustBeFoundedHexLevelOneRule(Board board) {
       this.board = board;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        return isHexAtLocationLevelOne(buildActionData.getHexBuildLocation()) ? successfulResult : failedResult;
    }

    private boolean isHexAtLocationLevelOne(Location hexLocation) {
       Hex boardHex = board.getHex(hexLocation);
       return boardHex.getLevel() == 1;
    }
}
