package tigerisland.build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Orientation;



public class SettlementAlreadyContainsTotoroRuleTest {

    private SettlementAlreadyContainsTotoroRule rule = null;
    private SettlementBoard board = null;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private PieceBoard pieces = null;
    private BuildActionResult result = null;
    private final String errorMessage = "Totoro build hex must be adjacent to a settlement that doesn't contain a totoro";


    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void test_ShouldThrowErrorWithSettlementAlreadyContainingTotoro(){

        //arrange
        pieces = new PieceBoardImpl();
        Location start = new Location(0,0,0);

        pieces.addPiece(new Villager(), start, player1.getId());
        Location previous = start;
        //set up settlement
        for (int i = 0; i<6;++i ){
            pieces.addPiece(new Villager(), previous.getAdjacent(Orientation.getEast()), player1.getId());
            previous = previous.getAdjacent(Orientation.getEast());
        }

        pieces.addPiece(new Totoro(), previous.getAdjacent(Orientation.getSouthWest()), player1.getId());

        board = new LazySettlementBoard(pieces);
        rule = new SettlementAlreadyContainsTotoroRule(board);

        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(start.getAdjacent(Orientation.getWest()));
        builder.withPlayer(player1);

        //act


        result = rule.applyRule(builder.build());

        //assert

        Assert.assertFalse(result.successful);

        Assert.assertEquals(errorMessage, result.errorMessage);
    }

    @Test

    public void test_ShouldBeSuccessfulWithSettlementContainingTigerButNoTotoro() {

        //arrange
        pieces = new PieceBoardImpl();
        Location start = new Location(0, 0, 0);

        pieces.addPiece(new Villager(), start, player1.getId());
        Location previous = start;
        //set up settlement
        for (int i = 0; i < 6; ++i) {
            pieces.addPiece(new Villager(), previous.getAdjacent(Orientation.getEast()), player1.getId());
            previous = previous.getAdjacent(Orientation.getEast());
        }

        pieces.addPiece(new Tiger(), previous.getAdjacent(Orientation.getSouthWest()), player1.getId());

        board = new LazySettlementBoard(pieces);
        rule = new SettlementAlreadyContainsTotoroRule(board);

        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(start.getAdjacent(Orientation.getWest()));
        builder.withPlayer(player1);

        //act


        result = rule.applyRule(builder.build());

        //assert

        Assert.assertTrue(result.successful);
    }


}