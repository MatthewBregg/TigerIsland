package tigerisland.build;

import tigerisland.build.rules.BuildActionRule;

import java.util.ArrayList;
import java.util.List;

public class FoundNewSettlementBuild implements BuildActionStrategy{

    private List<BuildActionRule> buildActionRules;

    public FoundNewSettlementBuild(BuildActionRule... buildActionRules) {

        this.buildActionRules = new ArrayList<>();
        for(BuildActionRule rule : buildActionRules) {
            this.buildActionRules.add(rule);
        }
    }

    @Override
    public BuildActionResult build(BuildActionData buildActionData) {

        for(BuildActionRule rule : buildActionRules) {

            BuildActionResult result = rule.applyRule(buildActionData);
            if (result.successful == false) {
                return result;
            }
        }
        return new BuildActionResult(true);
    }

}
