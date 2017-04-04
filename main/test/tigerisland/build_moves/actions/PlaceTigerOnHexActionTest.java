package tigerisland.build_moves.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.NullPiece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Tiger;
import tigerisland.player.Player;

public class PlaceTigerOnHexActionTest {
    private PieceBoard pieceBoard;
    private PlaceTigerOnHexAction tigerPlaceAction;

    @Before
    public void setup() {
        this.pieceBoard = new PieceBoardImpl();
        this.tigerPlaceAction = new PlaceTigerOnHexAction(pieceBoard);
    }

    @Test
    public void test_ShouldPlaceTigerOnHex() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        tigerPlaceAction.applyAction(buildActionData);

        // Assert
        Assert.assertFalse(pieceBoard.getPiece(hexLocation) instanceof NullPiece);
        Assert.assertTrue(pieceBoard.getPiece(hexLocation) instanceof Tiger);
    }

    @Test
    public void test_ShouldSubtractATigerFromPlayer() {

        // Arrange
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        int expectedTigers = player.getTigerCount() - 1;

        // Act
        tigerPlaceAction.applyAction(buildActionData);

        // Assert
        Assert.assertEquals(expectedTigers, player.getTigerCount());
    }







}