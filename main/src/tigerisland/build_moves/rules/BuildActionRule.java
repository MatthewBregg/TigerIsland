package tigerisland.build_moves.rules;


import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;

public interface BuildActionRule {
    BuildActionResult applyRule(BuildActionData buildActionData);
}
