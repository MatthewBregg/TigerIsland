package tigerisland.cucumbertest.steppers;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.TotoroBuild;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;

public class CucumberTotoroBuild {

    private TotoroBuild totoroBuild;

    private Board board;
    private PieceBoard pieceBoard;
    SettlementBoard settlementBoard;
    ScoreManager scoreManager;

    private Player player;
    private Hex buildHex;
    private Location buildTotoroLocation;
    int previousPlayerTotoroCount = -1;

    @Given("^A player has at least one totoro$")
    public void aPlayerHasAtLeastOneTotoro() {
        player = new Player();
        previousPlayerTotoroCount = player.getTotoroCount();
    }

    @And("^Player has a settlement of size five or greater$")
    public void playerHasASettlementOfSizeFiveOrGreater() {

        // Arrange
        board = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);

        Location referenceLocation = new Location(0, 0, 0);
        List<Location> piecesLocation = referenceLocation.getSurroundingLocations();
        piecesLocation.add(referenceLocation);

        for(Location location : piecesLocation) {

            board.placeHex(location, new Hex());
            pieceBoard.addPiece(new Villager(), location, player.getId());
        }
    }

    @And("^That settlment does not have a totoro$")
    public void playerSettlementDoesNotHaveATotoro() {

        Location referenceLocation = new Location(0, 0, 0);
        Settlement settlement = settlementBoard.getSettlement(referenceLocation);
        TotoroCounter tc = new TotoroCounter();
        settlement.acceptVisitor(tc);
        Assert.assertEquals(0,tc.getCount());
    }

    @And("^There is a non-volcano hex adjacent to the settlement$")
    public void boardHasANonVolcanoHexAdjacentToSettlement() {
        buildHex = new Hex();
        List<Location> surroundingLocations = new Location(0, 0, 0).getSurroundingLocations();
        buildTotoroLocation = getAdjacentLocationNotAlreadyUsedOnBoard(surroundingLocations.get(0));
        board.placeHex(buildTotoroLocation, buildHex);
    }

    @And("^that hex does not have any game pieces on it$")
    public void hexDoesNotHaveGamePiecesOnIt() {
        Assert.assertFalse(pieceBoard.isLocationOccupied(buildTotoroLocation));
    }

    @When("^Player builds totoro on that hex$")
    public void playerBuildsTotoroOnAHex() {

        // Arrange
        scoreManager = new ScoreManager();
        totoroBuild = new TotoroBuild(board, pieceBoard, settlementBoard, scoreManager);

        BuildActionData buildActionData = new BuildActionData.Builder()
                                            .withPlayer(player)
                                            .withHexLocation(buildTotoroLocation)
                                            .build();

        // Act
        totoroBuild.build(buildActionData);
    }

    @Then("^Totoro is placed on hex$")
    public void totoroIsPlaceOnHex() {
        Assert.assertTrue(pieceBoard.isLocationOccupied(buildTotoroLocation));
        Assert.assertTrue(pieceBoard.getPiece(buildTotoroLocation) instanceof Totoro);
    }

    @And("^player's totoro account is decremented by one$")
    public void playerTotoroCountDecrementsByOne() {
        Assert.assertEquals(previousPlayerTotoroCount-1, player.getTotoroCount());
    }

    private Location getAdjacentLocationNotAlreadyUsedOnBoard(Location location) {

        List<Location> surroundingLocations = location.getSurroundingLocations();
        for(Location loc: surroundingLocations) {

            if ( !pieceBoard.isLocationOccupied(location) )
                return loc;
        }
        return new Location(-1, -1);
    }

    private List<Location> createLocationAndAdjacentLocations(Location referenceLocation) {
       List<Location> locations = new ArrayList<>();

        int x = referenceLocation.getX();
        int y = referenceLocation.getY();
        int z = referenceLocation.getZ();

        locations.add(referenceLocation);

        // To tops hexes locations
        locations.add(new Location(x, y+1, z-1));
        locations.add(new Location(x+1, y, z-1));

         // Right hex location
        locations.add(new Location(x+1, y-1, z));

        // To bottom hexes locations
        locations.add(new Location(x, y-1, z+1));
        locations.add(new Location(x-1, y, z+1));

        // Left hex location
        locations.add(new Location(x+1, y+1, z));

        return locations;
    }

}
