package tigerisland.build_moves.rules;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.player.Player;

public class PlayerMustHaveATotoroToBuildRule implements BuildActionRule {
    private final String MUST_HAVE_TOTORO_ERROR = "Player must have at least one totoro to do this build";
    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, MUST_HAVE_TOTORO_ERROR);


    @Override
    public BuildActionResult applyRule(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        return player.getTotoroCount() > 0 ? successfulResult : failedResult;
    }
}
