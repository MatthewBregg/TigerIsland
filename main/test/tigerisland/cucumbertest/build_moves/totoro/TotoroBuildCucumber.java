package tigerisland.cucumbertest.build_moves.totoro;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.TotoroBuild;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Totoro;
import tigerisland.player.Player;

public class TotoroBuildCucumber {

    /*
    Given A player has at least 1 totoro
    And player has a settlement of size 5 or greater
    And that settlment does not have a totoro
    And there is a non-volcano hex adjacent to the settlement
    And and that hex does not have any game pieces on it
    When player builds totoro on that hex
    Then totoro is place on that hex
    And player's totoro account is decrement by 1
    */

    private TotoroBuild totoroBuild;
    private PieceBoard pieceBoard;
    private Player player;
    private Hex hex;
    private Location buildTotoroLocation;
    int previousPlayerTotoroCount = -1;

    @Given("^A player has at least 1 totoro$")
    public void aPlayerHasAtLeast1Totoro() {
        player = new Player();
        previousPlayerTotoroCount = player.getTotoroCount();
    }


    @When("^player builds totoro on a hex$")
    public void playerBuildsTotoroOnAHex() {

        // Arrange
        hex = new Hex();
        buildTotoroLocation = new Location(0, 0, 0);
        pieceBoard = new PieceBoardImpl();

        BuildActionData buildActionData = new BuildActionData.Builder()
                                            .withPlayer(player)
                                            .withHexLocation(buildTotoroLocation)
                                            .build();

        // Act
        totoroBuild.build(buildActionData);
    }

    @Then("^Totoro is place on hex$")
    public void totoroIsPlaceOnHex() {
        Assert.assertTrue(pieceBoard.isLocationOccupied(buildTotoroLocation));
        Assert.assertTrue(pieceBoard.getPiece(buildTotoroLocation) instanceof Totoro);
    }

    @And("^Player totoro count decrements by 1$")
    public void playerTotoroCountDecrementsBy1() {
        Assert.assertEquals(previousPlayerTotoroCount-1, player.getTotoroCount());
    }
}
