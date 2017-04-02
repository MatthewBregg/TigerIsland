package tigerisland.cucumbertest.piece_and_settlement_builds;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionStrategy;
import tigerisland.build_moves.builds.FoundNewSettlementBuild;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;


public class CucumberSettlementBuildStepper implements En {

    Player player;
    HexBoard board;
    PieceBoard pieces;
    Location location;
    BuildActionData data;
    BuildActionStrategy foundSettlement;
    Hex hex;
    Terrain rocky = Rocky.getInstance();
    int settlementID = 1;
    int tileID = 2;
    int initialVillagerCount;


    private void setUp(){
        //todo possibly run this with tile placement
        player = new Player(20,3,2);

        initialVillagerCount = player.getVillagerCount();
        location = new Location(3,-3,0);

        hex = new Hex(tileID,settlementID,rocky,1);
        board = new HexBoard();
        board.placeHex(location, hex);

        pieces = new PieceBoardImpl();

        data = new BuildActionData.Builder().withPlayer(player)
                .withHexLocation(location)
                .withTerrain(Rocky.getInstance()).build();

        foundSettlement= new FoundNewSettlementBuild(board, pieces);
    }
    public CucumberSettlementBuildStepper() {
        Given("^Player has at least (\\d+) villager$", (Integer numVillagers) -> {
            setUp();
            Assert.assertTrue(player.getVillagerCount()>=numVillagers.intValue());
        });
        When("^Player attempts to place the villager on a non-volcano hex$", () -> {
            Assert.assertFalse(pieces.isLocationOccupied(location, player.getId()));
        });
        And("^the hex has a level of (\\d+)$", (Integer level) -> {
            Assert.assertEquals(level.intValue(),board.getHex(location).getLevel());
        });
        And("^the hex is unoccupied$", () -> {
            Assert.assertFalse(pieces.isLocationOccupied(location,player.getId()));
        });
        Then("^A new settlement of size (\\d+) is formed on that tile, one villager is subtracted from players villager count, and appears on that tile$", (Integer settlementSize) -> {
            foundSettlement.build(data);
            SettlementBoard settlementBoard = new LazySettlementBoard(pieces);

            Settlement settlement = settlementBoard.getSettlement(location, player.getId());

            Assert.assertEquals(settlementSize.intValue(), settlement.settlementSize());

            Assert.assertTrue(player.getVillagerCount() == initialVillagerCount-1);


        });
    }
}
