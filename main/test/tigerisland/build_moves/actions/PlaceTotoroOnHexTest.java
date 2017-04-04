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

public class PlaceTotoroOnHexTest {

    private PieceBoard pieceBoard;
    private PlaceTotoroOnHexAction totoroOnHexAction;

    @Before
    public void setup() {
        this.pieceBoard = new PieceBoardImpl();
        this.totoroOnHexAction = new PlaceTotoroOnHexAction(pieceBoard);
    }

    @Test
    public void test_ShouldPlaceTotoroOnHex() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        totoroOnHexAction.applyAction(buildActionData);

        // Assert
        Assert.assertFalse(pieceBoard.getPiece(hexLocation) instanceof NullPiece);
    }

    @Test
    public void test_ShouldSubtractATotoroFromPlayer() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        int expectedTotoros = player.getTotoroCount() - 1;

        // Act
        totoroOnHexAction.applyAction(buildActionData);

        // Assert
        Assert.assertEquals(expectedTotoros, player.getTotoroCount());
    }
}
