package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionStrategy;
import tigerisland.game.GameManager;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;
import tigerisland.test_boards.TestingBoardMaker;

import java.util.ArrayList;

public class ExpandSettlementInsufficient implements En {


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


    public ExpandSettlementInsufficient() {
        Given("^Player has an existing settlement Scenario insufficient villagers$", () -> {
           expansionSetup(null);
            Location existingSettlementLocation = new Location (1, -1, 0);
            PieceBoard placedPieces = manager.getPieceBoard();
            Assert.assertTrue(placedPieces.getPiece(existingSettlementLocation, player1.getId()) instanceof Villager);
            previousVillagerCount = player1.getVillagerCount();
        });
        When("^Player chooses to expand a settlement to one of the adjacent terrain types$", () -> {
            Assert.assertFalse(manager.expandSettlement(new Location(1,-1,0), Lake.getInstance(), player1));
        });
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
        player1.removeVillagers(19);
        players.add(player1);
        players.add(player2);

        testBoardMaker = new TestingBoardMaker(testBoard, players);

        board = testBoardMaker.getBoard();
        pieces = testBoardMaker.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(board,players,pieces);
        settlementBoard = new LazySettlementBoard(pieces);

    }




}
