package tigerisland.build_moves.actions;

import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreSettlementExpansion implements MakeBuildAction {

    private ScoreManager scoreManager;
    private SettlementExpansionUtility settlementExpansionUtility;

    public ScoreSettlementExpansion(ScoreManager scoreManager, SettlementExpansionUtility settlementExpansionUtility) {
        this.scoreManager = scoreManager;
        this.settlementExpansionUtility = settlementExpansionUtility;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        int calculatedScore = settlementExpansionUtility.getScoreOnExpansion(buildActionData);
        Player player = buildActionData.getPlayer();
        scoreManager.addScore(player.getId(),calculatedScore);
    }
}
