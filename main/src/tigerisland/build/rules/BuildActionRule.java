package tigerisland.build.rules;


import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;

public interface BuildActionRule {
    public BuildActionResult applyRule(BuildActionData buildActionData);
}
