package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.TestLogger;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionStrategy;
import tigerisland.build_moves.builds.FoundNewSettlementBuild;
import tigerisland.game.GameManager;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;
import tigerisland.test_boards.TestingBoardMaker;

import java.util.ArrayList;
import java.util.Set;


public class SettlementBuildValidation implements En {

    //variables for non volcano placement
    private Player player1;
    private Player player2;
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

    //variables for settlmentExpansion feature
    private GameManager manager;
    private TestingBoardMaker testBoardMaker;
    private ArrayList<Player> players;
    private HexBoard hexBoard;
    private SettlementBoard settlementBoard;
    int previousVillagerCount;
    int villagersRequired;

    private String testBoard [];


    public SettlementBuildValidation() {

        //Non volcano placement feature

        Given("^Player has at least (\\d+) villager$", (Integer numVillagers) -> {
            nonVolcanoSetUp();
            Assert.assertTrue(player1.getVillagerCount()>=numVillagers.intValue());
        });
        When("^Player attempts to place the villager on a non-volcano hex$", () -> {
            Assert.assertFalse(pieces.isLocationOccupied(location, player1.getId()));
        });
        And("^the hex has a level of (\\d+)$", (Integer level) -> {
            Assert.assertEquals(level.intValue(),board.getHex(location).getLevel());
        });
        And("^the hex is unoccupied$", () -> {
            Assert.assertFalse(pieces.isLocationOccupied(location,player1.getId()));
        });
        Then("^A new settlement of size (\\d+) is formed on that tile, one villager is subtracted from players villager count, and appears on that tile$", (Integer settlementSize) -> {
            foundSettlement.build(data);
            SettlementBoard settlementBoard = new LazySettlementBoard(pieces);

            Settlement settlement = settlementBoard.getSettlement(location, player1.getId());

            Assert.assertEquals(settlementSize.intValue(), settlement.settlementSize());

            Assert.assertTrue(player1.getVillagerCount() == initialVillagerCount-1);

            //TODO is tile necessary here? or can we just use hex location


        });


        //expand settlement feature
        Given("^Player has an existing settlement$", () -> {
           expansionSetup("SettlementBuildValidationBoard.txt");



//           /Users/josh/Documents/gitRepos/CEN3031/TigerIsland/main/test/tigerisland/test_boards/SettlementBuildValidationBoard.txt
           Location existingSettlementLocation = new Location (1, -1, 0);
           PieceBoard placedPieces = manager.getPieceBoard();
           Assert.assertTrue(placedPieces.getPiece(existingSettlementLocation, player1.getId()) instanceof Villager);
           previousVillagerCount = player1.getVillagerCount();
        });
        When("^Player expands the settlement$", () -> {
           Assert.assertTrue(manager.expandSettlement(new Location(1,-1,0), Lake.getInstance(), player1));
        });
        Then("^Player’s villagers are placed on all connected hexes of that terrain type$", () -> {

            Location existingSettlementLocation = new Location (1, -1, 0);

                BuildActionData.Builder data = new BuildActionData.Builder();
                data.withPlayer(player1);
                data.withSettlementLocation(existingSettlementLocation);
                data.withTerrain(Lake.getInstance());

            pieces = testBoardMaker.getPieces();
            settlementBoard = new LazySettlementBoard(pieces);
            board = testBoardMaker.getBoard();
            SettlementExpansionUtility expander = new SettlementExpansionUtility(board, pieces, settlementBoard);
            Set<Location> expansionLocations = expander.getExpandableHexes(data.build());

            villagersRequired = expander.getVillagersNeededToExpand(data.build());

            expansionLocations.add(existingSettlementLocation);

            board = manager.getHexBoard();
            pieces = manager.getPieceBoard();
            settlementBoard = new LazySettlementBoard(pieces);

            for (Location location: board.getUsedBoardLocations()){
                if (expansionLocations.contains(location ))
                    Assert.assertTrue(pieces.isLocationOccupied(location, player1.getId()));
                else
                    Assert.assertFalse(pieces.isLocationOccupied(location,player1.getId()));
            }

        });
        And("^player’s villagers are decremented by an amount equal to the number of villagers placed$", () -> {
            Assert.assertTrue(player1.getVillagerCount()==previousVillagerCount-villagersRequired);
        });
    }

    private void nonVolcanoSetUp(){
        //todo possibly run this with tile placement if necessary
        player1 = new Player(20,3,2, 1,new PlayerID());

        initialVillagerCount = player1.getVillagerCount();
        location = new Location(3,-3,0);

        hex = new Hex(tileID, settlementID,rocky,1);
        board = new HexBoard();
        board.placeHex(location, hex);

        scoreManager = new ScoreManager();

        pieces = new PieceBoardImpl();

        data = new BuildActionData.Builder().withPlayer(player1)
                .withHexLocation(location)
                .withTerrain(Rocky.getInstance()).build();

        foundSettlement= new FoundNewSettlementBuild(board, pieces, scoreManager);
    }

    private void expansionSetup(String boardPath){

        String testName [] = {"0 0 0 3 0 volcano null 0",
              "1 0 -1 3 0 lake null 1",
              "0 1 -1 3 0 jungle null 1",
              "-1 0 1 3 0 rocky null 1",
              "0 -1 1 3 0 grassland null 0",
              "1 -1 0 3 1 lake villager 0",
              "2 -2 0 3 1 volcano null 1",
              "2 -1 -1 3 1 lake null 1",
              "3 -3 0 3 2 lake villager 1",
              "3 -2 -1 3 2 lake null 1",
              "4 -3 -1 1 2 volcano null 1"};

        testBoard = testName;

        players = new ArrayList();
        player1 = new Player();
        player2 = new Player();
        players.add(player1);
        players.add(player2);

        testBoardMaker = new TestingBoardMaker(testBoard, players);

        board = testBoardMaker.getBoard();
        pieces = testBoardMaker.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(board,players,pieces, new TestLogger());
        settlementBoard = new LazySettlementBoard(pieces);

    }
}
