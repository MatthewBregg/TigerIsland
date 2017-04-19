package tigerisland.build_moves.actions;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreShamanOnHex implements MakeBuildAction{


    private final int SHAMAN_SCORE = 1;
    private ScoreManager scoreManager;

    public ScoreShamanOnHex(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        scoreManager.addScore(player.getId(), SHAMAN_SCORE);
    }
}
