
/* This action would be for
1) Build Totoro
*/

package tigerisland.build_moves.actions;

import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreTotoroOnHex implements MakeBuildAction {

    private final int TOTORO_SCORE = 200;
    private ScoreManager scoreManager;

    public ScoreTotoroOnHex(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        scoreManager.addScore(player.getId(), TOTORO_SCORE);
    }
}
