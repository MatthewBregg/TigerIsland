package tigerisland.cucumbertest.tile_placement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.Board;
import tigerisland.HexBoard;
import tigerisland.Location;
import tigerisland.Tile;
import tigerisland.tile_placement.AdjacentToBoardTilePlacer;
import tigerisland.tile_placement.InvalidTilePlacer;
import tigerisland.tile_placement.TilePlacement;
import tigerisland.tile_placement.TilePlacementManager;

public class TilePlacementCucumberTest {

    TilePlacement tilePlacementManager;
    Board board;

    @Given("^No other tiles are on the game board$")
    public void noOtherTilesAreOnTheGameBoard() throws Throwable {

        // Setup
        board = new HexBoard();

        TilePlacement invalidTilePlacer = new InvalidTilePlacer();

        AdjacentToBoardTilePlacer adjacentToBoard = new AdjacentToBoardTilePlacer(board, null);
        adjacentToBoard.setNextTilePlacement(invalidTilePlacer);

        tilePlacementManager = new TilePlacementManager(adjacentToBoard);
    }

    @When("^A tile is place at a location$")
    public void aTileIsPlaceAtALocation() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        tilePlacementManager.placeTile(tile, location);
    }

    @Then("^The tile is place at that location on the board$")
    public void thenTileIsPlaceAtThatLocationOnTheBoard() throws Throwable {

        int expectedBoardSize = 3;

        // Assert
        Assert.assertEquals(expectedBoardSize, board.getSize());
    }


}
