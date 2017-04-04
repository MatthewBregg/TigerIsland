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
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Totoro;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;

public class TotoroBuildCucumber {

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

    @And("^player has a settlment of size five or greater$")
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

    @And("^player settlement does not have a totoro$")
    public void playerSettlementDoesNotHaveATotoro() {

        Location referenceLocation = new Location(0, 0, 0);
        Settlement settlement = settlementBoard.getSettlement(referenceLocation);
        // how do I know if this settlment has a totoro?
    }

    @And("Board has a non volcano hex adjacent to settlment")
    public void boardHasANonVolcanoHexAdjacentToSettlement() {
        buildHex = new Hex();
        List<Location> surroundingLocations = new Location(0, 0, 0).getSurroundingLocations();
        buildTotoroLocation = getAdjacentLocationNotAlreadyUsedOnBoard(surroundingLocations.get(0));
        board.placeHex(buildTotoroLocation, buildHex);
    }

    @And("^Hex does not have game pieces on it$")
    public void hexDoesNotHaveGamePiecesOnIt() {
        Assert.assertFalse(pieceBoard.isLocationOccupied(buildTotoroLocation));
    }

    @When("^player builds totoro on a hex$")
    public void playerBuildsTotoroOnAHex() {

        // Arrange
        scoreManager = new ScoreManager();
        totoroBuild = new TotoroBuild(board, pieceBoard, settlementBoard, scoreManager);
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

    @And("^Player totoro count decrements by one$")
    public void playerTotoroCountDecrementsByOne() {
        Assert.assertEquals(previousPlayerTotoroCount-1, player.getTotoroCount());
    }

    private Location getAdjacentLocationNotAlreadyUsedOnBoard(Location location) {

        List<Location> surroundingLocations = location.getSurroundingLocations();
        for(Location loc: surroundingLocations) {

            if ( !pieceBoard.isLocationOccupied(location) )
                return loc;
        }
        return new Location(-1, -1, -1);
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
