package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.builds.TigerBuild;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Rocky;
import tigerisland.tile.Orientation;

public class CucumberTigerBuild implements En {

    private TigerBuild tigerBuild;

    private Board board;
    private PieceBoard pieceBoard;
    SettlementBoard settlementBoard;
    ScoreManager scoreManager;

    private Player player;
    private Hex buildHex;
    private Location buildTigerLocation;
    private int previousPlayerTigerCount = -1;
    private Location settlementLocation;
    private int previousScore;

    public CucumberTigerBuild() {



        Given("^A player has at least (\\d+) tiger$", (Integer minCount) -> {
            player = new Player();
            previousPlayerTigerCount = player.getTigerCount();
            Assert.assertTrue(previousPlayerTigerCount>=minCount.intValue());
        });

        And("^The player has a settlement that does not have a tiger$", () -> {
            settlementLocation = new Location(0, 0, 0);

            pieceBoard = new PieceBoardImpl();
            pieceBoard.addPiece(new Villager(),settlementLocation,player.getId());

            settlementBoard = new LazySettlementBoard(pieceBoard);

            Settlement settlement = settlementBoard.getSettlement(settlementLocation);
            TigerCounter tc = new TigerCounter();
            settlement.acceptVisitor(tc);
            Assert.assertEquals(0,tc.getCount());

        });
        And("^The build hex has a level >= (\\d+)$", (Integer buildLevel) -> {
            buildTigerLocation = settlementLocation.getAdjacent(Orientation.getEast());
            buildHex =  new Hex(0, 0, Rocky.getInstance(),buildLevel.intValue());
            board = new HexBoard();
            board.placeHex(buildTigerLocation,buildHex);

            Assert.assertTrue(board.getHex(buildTigerLocation).getLevel()>= buildLevel.intValue());
        });
        And("^the build hex does not have any game pieces on it", () -> {
            Assert.assertTrue(pieceBoard.getPiece(buildTigerLocation).getClass()==NullPiece.class);
        });

        When("^Player builds tiger on that hex$", () -> {
            scoreManager = new ScoreManager();
            previousScore = scoreManager.getPlayerScore(player.getId());
            tigerBuild = new TigerBuild(board, pieceBoard, settlementBoard, scoreManager);
            

            BuildActionData buildActionData = new BuildActionData.Builder()
                    .withPlayer(player)
                    .withHexLocation(buildTigerLocation)
                    .build();

            BuildActionResult result = tigerBuild.build(buildActionData);
            System.out.print("bob");

        });
        Then("^Tiger is placed on hex$", () -> {
            Assert.assertTrue(pieceBoard.isLocationOccupied(buildTigerLocation));
            Assert.assertTrue(pieceBoard.getPiece(buildTigerLocation) instanceof Tiger);

            Assert.assertTrue(pieceBoard.getPiece(buildTigerLocation).getClass()==Tiger.class);

        });


        And("^The player's tiger amount is decremented by 1", () -> {
           Assert.assertTrue(player.getTigerCount()==previousPlayerTigerCount-1);
        });
        And("^The player's score is increased by (\\d+)$", (Integer scoreIncrease) -> {
            Assert.assertTrue(scoreManager.getPlayerScore(player.getId())==previousScore+scoreIncrease.intValue());
        });


    }
}
