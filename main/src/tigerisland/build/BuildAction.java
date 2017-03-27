package tigerisland.build;

import tigerisland.build.actions.MakeBuildAction;
import tigerisland.build.rules.BuildActionRule;

import java.util.List;

public abstract class BuildAction implements BuildActionStrategy {


    List<BuildActionRule> rules;
    List<MakeBuildAction> actions;

    @Override
    public BuildActionResult build(BuildActionData buildActionData) {

        for(BuildActionRule rule : rules) {
            BuildActionResult result = rule.applyRule(buildActionData);
            if (result.successful == false) {
                return result;
            }
        }

        for(MakeBuildAction action : actions) {
            action.applyAction(buildActionData);
        }

        return new BuildActionResult(true);
    }

    public abstract List<BuildActionRule> createBuildActionRules();

    public abstract List<MakeBuildAction> createBuildActions();
}
