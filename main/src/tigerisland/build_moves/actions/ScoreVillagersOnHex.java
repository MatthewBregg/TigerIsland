/* This action would be for
1) Founding settlement
2) Expanding settlement-- we call this for each hex?
 */

package tigerisland.build_moves.actions;

import tigerisland.board.Board;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreVillagersOnHex implements MakeBuildAction {

    private ScoreManager scoreManager;
    private Board board;
    private boolean justNukedShaman;

    public ScoreVillagersOnHex(Board board, ScoreManager scoreManager, boolean justNukedShaman) {
        this.board = board;
        this.scoreManager = scoreManager;
        this.justNukedShaman = justNukedShaman;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        if (justNukedShaman == false) {
            Player player = buildActionData.getPlayer();
            Hex hex = board.getHex(buildActionData.getHexBuildLocation());

            int hexLevel = hex.getLevel();

            scoreManager.addScore(player.getId(), hexLevel);
        }
        // if the shaman was just nuked, don't give any extra score
        else if (justNukedShaman == true){
            Player player = buildActionData.getPlayer();
            Hex hex = board.getHex(buildActionData.getHexBuildLocation());

            int hexLevel = hex.getLevel();
        }
    }

}
