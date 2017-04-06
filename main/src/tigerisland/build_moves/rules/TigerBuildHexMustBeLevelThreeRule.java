package tigerisland.build_moves.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.hex.Hex;


public class TigerBuildHexMustBeLevelThreeRule implements BuildActionRule {

    private final String BUILD_HEX_MUST_BE_LEVEL_THREE_ERROR = "The tiger build hex must be level 3 or greater";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, BUILD_HEX_MUST_BE_LEVEL_THREE_ERROR);
    private Board board;


     public TigerBuildHexMustBeLevelThreeRule(Board board){
         this.board = board;
     }



    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Location location  = buildActionData.getHexBuildLocation();
        Hex hex = board.getHex(location);

        int level = hex.getLevel();

        return level >= 3 ? successfulResult : failedResult;
    }
}






