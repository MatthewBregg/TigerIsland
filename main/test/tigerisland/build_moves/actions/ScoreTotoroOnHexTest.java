package tigerisland.build_moves.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.NullPiece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;

public class ScoreTotoroOnHexTest {

    private ScoreManager scoreManager;
    private ScoreTotoroOnHex totorScoreAction;

    @Before
    public void setup() {
        this.scoreManager = new ScoreManager();
        this.totorScoreAction = new ScoreTotoroOnHex(scoreManager);
    }

    @Test
    public void test_ShouldAddScoreToPlayer() {

        // Arrange
        Player player = new Player();

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .build();

        // Act
        totorScoreAction.applyAction(buildActionData);

        // Assert
        int expectedScore = 200;
        PlayerID playerID = player.getId();
        Assert.assertEquals(expectedScore, scoreManager.getPlayerScore(playerID));
    }
}
