package tigerisland.build.actions;

import tigerisland.board.Board;
import tigerisland.build.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;

public class ScoreVillagersOnHex implements MakeBuildAction {

    private ScoreManager scoreManager;
    private Board board;

    public ScoreVillagersOnHex(Board board, ScoreManager scoreManager) {
        this.board = board;
        this.scoreManager = scoreManager;
    }

    @Override
    public void applyAction(BuildActionData buildActionData) {
        Player player = buildActionData.getPlayer();
        Hex hex = board.getHex(buildActionData.getHexBuildLocation());
        int hexLevel = hex.getLevel();
        scoreManager.addScore(player.getId(), hexLevel);
    }
}
