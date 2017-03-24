package tigerisland.build.rules;

import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.player.Player;

public class NotEnoughVillagersRule implements BuildActionRule{

    private final String NOT_ENOUGH_VILLAGERS_ERROR = "Player does not have enough villagers";

    private BuildActionResult successfulResult = new BuildActionResult(true);
    private BuildActionResult failedResult = new BuildActionResult(false, NOT_ENOUGH_VILLAGERS_ERROR);


    @Override
    public BuildActionResult applyRule(BuildActionData buildAction) {
       Player player = buildAction.getPlayer();
       return playerHasNoVillagersLeft(player) ? failedResult : successfulResult;
    }

    private boolean playerHasNoVillagersLeft(Player player) {
        return player.getVillagerCount() <= 0;
    }
}
