package tigerisland.build.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.hex.Hex;
import tigerisland.terrains.Volcano;

public class NoVolcanoForBuildLocationRule implements BuildActionRule {

    private Board board;

    private final String VOLCANO_HEX_ERROR = "Cannot build on a volcano hex";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, VOLCANO_HEX_ERROR);

    public NoVolcanoForBuildLocationRule(Board board) {
        this.board = board;
    }

    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Location hexLocation = buildActionData.getHexBuildLocation();
        return isBuildLocationAVolcanoHex(hexLocation) ? failedResult : successfulResult;
    }

    private boolean isBuildLocationAVolcanoHex(Location hexLocation) {
        Hex boardHex = board.getHex(hexLocation);
        return boardHex.getTerrain().equals(Volcano.getInstance());
    }
}
