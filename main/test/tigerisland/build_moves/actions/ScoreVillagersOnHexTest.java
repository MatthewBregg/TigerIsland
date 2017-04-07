package tigerisland.build_moves.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;

public class ScoreVillagersOnHexTest {

    private Board board;
    private ScoreManager scoreManager;
    private ScoreVillagersOnHex villagersScoreAction;

    @Before
    public void setup() {

        this.board = new HexBoard();
        this.scoreManager = new ScoreManager();
        this.villagersScoreAction = new ScoreVillagersOnHex(board, scoreManager);
    }

    @Test
    public void test_ShouldAddScoreToPlayer() {

        // Arrange
        int hexLevel = 3;
        Hex hex = new Hex(0); hex.setLevel(hexLevel);
        Player player = new Player();
        Location location = new Location(0, 0, 0);

        board.placeHex(location, hex);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(location)
                .build();

        // Act
        villagersScoreAction.applyAction(buildActionData);

        // Assert
        PlayerID playerID = player.getId();
        Assert.assertEquals(hexLevel, scoreManager.getPlayerScore(playerID));
    }
}
