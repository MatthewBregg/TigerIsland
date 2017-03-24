package tigerisland.cucumbertest.board;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.AdjacentToBoardTilePlacer;
import tigerisland.tile_placement.FirstTilePlacer;
import tigerisland.tile_placement.InvalidTilePlacer;
import tigerisland.tile_placement.TilePlacement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CucumberBoardTester {
    private Tile tile = null;

    private Board board = null;

    private List<Location> unPlacedLocations = null;
    private List <Location> placedLocations = null;

    private Location location = null;

    private TilePlacement placer = null;

    static int tileID = 0;


    @Given("^No other tiles are on the board$")
    public void weCheckIfBoardIsEmpty() throws Throwable {
        board = new HexBoard();
         unPlacedLocations = board.getUsedBoardLocations();
        assert ( unPlacedLocations.isEmpty());
    }

    @When("^A player attempts to place a tile on the board$")
    public void weAttemptToPlaceATileOnTheBoard() throws Throwable {
        //technically player's tile placer will place tile I- not sure on how our design handles this part-- we will need to update test at some point?
        tile = createValidTile();
        createTilePlacer();
        location = new Location (0,0,0);
        placer.placeTile(tile, location);
    }

    private Tile createValidTile() {
        ++tileID;
        return new Tile(tileID, new Hex(tileID, Volcano.getInstance()), new Hex(tileID,Grassland.getInstance()), new Hex(tileID,Rocky.getInstance()) );
    }
    private void createTilePlacer() {
        AdjacentToBoardTilePlacer adjPlacer = new AdjacentToBoardTilePlacer(board);
        adjPlacer.setNextTilePlacement(new InvalidTilePlacer());
        FirstTilePlacer firstTilePlacer = new FirstTilePlacer(board);
        firstTilePlacer.setNextTilePlacement(adjPlacer);
        placer =  firstTilePlacer;
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


    Location placedLocationFirstTile = null;
    Location placedLocationSecondTile = null;
    @Given("^The board is not empty$")
    public void theBoardIsNotEmpty() throws Throwable {
       board = new HexBoard();
       placedLocations = board.getUsedBoardLocations();
       placedLocationFirstTile = new Location(0,0,0);
       tile = createValidTile();
       createTilePlacer();
       placer.placeTile(tile,placedLocationFirstTile);
    }

    @When("^A player attempts to put a tile down at an unoccupied position AND the position has one edge in contact" +
            " with another tiles' edge AND the placed tile has all hexes on the same level$")
    public void aPlayerAttemptsToPutATileDownAtAnUnoccupiedPositionANDThePositionHasOneEdgeInContactWithAnotherTilesEdgeANDThePlacedTileHasAllHexesOnTheSameLevel() throws Throwable {
        placedLocationSecondTile = placedLocationFirstTile.getAdjacent(Orientation.getEast()).getAdjacent(Orientation.getEast());
        placer.placeTile(createValidTile(),placedLocationSecondTile);
    }

    @Then("^The tile is placed at that position on the board at level (\\d+) AND At least one edge is adjacent to an existing tile, AND no tiles overlap$")
    public void theTileIsPlacedAtThatPositionOnTheBoardAtLevelANDAtLeastOneEdgeIsAdjacentToAnExistingTileANDNoTilesOverlap(int arg0) throws Throwable {
        Set<Hex> hexagons = new HashSet<>();
        for ( Location loc : board.getUsedBoardLocations() ) {
            hexagons.add(board.getHex(loc));
        }

        for ( Hex hex : hexagons ) {
            Assert.assertTrue(hex.getLevel() == 1);
        }
        Assert.assertTrue(hexagons.size() == 6);
        Assert.assertTrue(new HashSet<Location>(board.getUsedBoardLocations()).size() == 6);
        // Confirms not overlapping, as none of the hexes are the same.

        boolean foundAdj = false;
        for ( Location loci : board.getUsedBoardLocations() ) {
            for ( Location locj : board.getUsedBoardLocations() ) {
                if ( board.getHex(locj).getTileID() != board.getHex(loci).getTileID()) {
                    if ( loci.getSurroundingLocations().contains(locj) ) {
                        foundAdj = true;
                    }
                }
            }
        }
        Assert.assertTrue(foundAdj);
    }
}
