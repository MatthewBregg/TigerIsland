package tigerisland.build_moves.actions;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreTigerOnHex implements MakeBuildAction{


    private final int TIGER_SCORE = 75;
    private ScoreManager scoreManager;

    public ScoreTigerOnHex(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        scoreManager.addScore(player.getId(), TIGER_SCORE);
    }
}
