package tigerisland.build_moves.builds;

import java.util.HashMap;
import java.util.Map;

public class BuildActionStrategyRegistry {

    Map<BuildType, BuildActionStrategy> buildActions;

    BuildActionStrategyRegistry() {
     buildActions = new HashMap<>();
    }

    public BuildActionResult build(BuildType buildType, BuildActionData buildAction) {
        return buildActions.containsKey(buildType)
               ? buildActions.get(buildType).build(buildAction)
               : createNotSupportedBuildAction(buildType);
    }

    public void addBuildActionStrategy(BuildType buildType, BuildActionStrategy buildActionStrategy) {
        this.buildActions.put(buildType, buildActionStrategy);
    }

    private BuildActionResult createNotSupportedBuildAction(BuildType buildType) {
        String errorMessage =  String.format("Build Action Type: %1 is not supported", buildType.toString());
        return new BuildActionResult(false, errorMessage);
    }
}
