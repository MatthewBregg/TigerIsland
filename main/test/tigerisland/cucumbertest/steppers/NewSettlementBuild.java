package tigerisland.cucumbertest.steppers;

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
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;


public class NewSettlementBuild implements En {

    private Player player;
    private HexBoard board;
    private PieceBoard pieces;
    private ScoreManager scoreManager;
    private Location location;
    private BuildActionData data;
    private BuildActionStrategy foundSettlement;
    private Hex hex;
    private Terrain rocky = Rocky.getInstance();
    private int settlementID = 1;
    private int initialVillagerCount;
    private int tileID = 3;


    private void setUp(){
        //todo possibly run this with tile placement if necessary
        player = new Player(20,3,2, new PlayerID());

        initialVillagerCount = player.getVillagerCount();
        location = new Location(3,-3,0);

        hex = new Hex(tileID, settlementID,rocky,1);
        board = new HexBoard();
        board.placeHex(location, hex);

        scoreManager = new ScoreManager();

        pieces = new PieceBoardImpl();

        data = new BuildActionData.Builder().withPlayer(player)
                .withHexLocation(location)
                .withTerrain(Rocky.getInstance()).build();

        foundSettlement= new FoundNewSettlementBuild(board, pieces, scoreManager);
    }
    public NewSettlementBuild() {
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

            //TODO is tile necessary here? or can we just use hex location


        });
    }
}
