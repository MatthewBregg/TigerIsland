package tigerisland.build_moves.rules;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.player.Player;

public class PlayerMustHaveAShamanToBuildRule implements BuildActionRule {

    private final String MUST_HAVE_SHAMAN_ERROR = "Player must have at least one shaman to do this build";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, MUST_HAVE_SHAMAN_ERROR);


    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        return player.getShamanCount() > 0 ? successfulResult : failedResult;
    }
}