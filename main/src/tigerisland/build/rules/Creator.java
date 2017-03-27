package tigerisland.build.rules;

import tigerisland.build.BuildActionStrategy;

public interface Creator {
    public BuildActionStrategy createBuildActionStrategy();
}
