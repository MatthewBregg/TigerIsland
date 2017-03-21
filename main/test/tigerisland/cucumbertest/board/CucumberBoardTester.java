package tigerisland.cucumbertest.board;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.AdjacentToBoardTilePlacer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbregg on 3/16/17.
 */
public class CucumberBoardTester {
    private Tile tile = null;

    private Board board = null;

    private List<Location> unPlacedLocations = null;
    private List <Location> placedLocations = null;

    private Location location = null;

    private AdjacentToBoardTilePlacer placer = null;


    @Given("^No other tiles are on the board$")
    public void weCheckIfBoardIsEmpty() throws Throwable {
        board = new HexBoard();
         unPlacedLocations = board.getUsedBoardLocations();
        assert ( unPlacedLocations.isEmpty());
    }

    @When("^A player attempts to place a tile on the board$")
    public void weAttemptToPlaceATileOnTheBoard() throws Throwable {
        //technically player's tile placer will place tile I- not sure on how our design handles this part-- we will need to update test at some point?
        tile = new Tile(3, Orientation.getEast() );
        placer = new AdjacentToBoardTilePlacer(board);
        location = new Location (0,0,0);
        placer.placeTile(tile, location);
    }

    @Then("^The tile is placed at the center of the board AND All the Hexes have level one$")
    public void weFindTileInCenterAndHexesAre1() throws Throwable {

        placedLocations = board.getUsedBoardLocations();

        List <Location> placedLocationsShouldBe = new ArrayList();

        placedLocationsShouldBe.add(new Location(0,0,0));
        placedLocationsShouldBe.add(new Location(-1,0,1));
        placedLocationsShouldBe.add(new Location(0,-1,1));

        assert(placedLocations.size()==3);
        assert(placedLocations.contains(placedLocationsShouldBe));

        for (Location placedLocation : placedLocations){
            Hex placedHex = board.getHex(placedLocation);
            assert(placedHex.getLevel()==1);
        }

    }
}
