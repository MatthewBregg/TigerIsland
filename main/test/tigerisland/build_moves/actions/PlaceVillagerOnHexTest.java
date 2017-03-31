package tigerisland.build_moves.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.actions.PlaceVillagerOnHexAction;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.NullPiece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.player.Player;

public class PlaceVillagerOnHexTest {

    private PieceBoard pieceBoard;
    private PlaceVillagerOnHexAction villagerOnHexAction;

    @Before
    public void setup() {
        this.pieceBoard = new PieceBoardImpl();
        this.villagerOnHexAction = new PlaceVillagerOnHexAction(pieceBoard);
    }

    @Test
    public void test_ShouldPlaceVillagerOnHex() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        villagerOnHexAction.applyAction(buildActionData);

        // Assert
        Assert.assertFalse(pieceBoard.getPiece(hexLocation) instanceof NullPiece);
    }

    @Test
    public void test_ShouldSubtractAVillagerFromPlayer() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        int expectedVillagers = player.getVillagerCount() - 1;

        // Act
        villagerOnHexAction.applyAction(buildActionData);

        // Assert
        Assert.assertEquals(expectedVillagers, player.getVillagerCount());
    }
}
