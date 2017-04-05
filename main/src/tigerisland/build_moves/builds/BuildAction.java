package tigerisland.build_moves.builds;

import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.rules.BuildActionRule;

import java.util.List;

public abstract class BuildAction implements BuildActionStrategy {

    private List<BuildActionRule> rules;
    private List<MakeBuildAction> actions;


    public BuildAction(){

    }



    private void InitializeLists(){
        if ( actions == null ) {
            actions = this.createBuildActions();
        }
        if ( rules == null ) {
            rules = this.createBuildActionRules();
        }

    }

    @Override
    public BuildActionResult build(BuildActionData buildActionData){

        this.InitializeLists();

        for(BuildActionRule rule : rules) {
            BuildActionResult result = rule.applyRule(buildActionData);

            if (!result.successful) {
                return result;
            }
        }
        for(MakeBuildAction action : actions) {
            action.applyAction(buildActionData);
        }

        return new BuildActionResult(true);
    }

    protected abstract List<BuildActionRule> createBuildActionRules();

    protected abstract List<MakeBuildAction> createBuildActions();
}
