package build_moves.rules;

import org.junit.Assert;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.TotoroBuildHexAdjacentToSettlementSizeFiveRule;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Orientation;

public class TotoroBuildHexAdjacentToSettlementSizeFiveRuleTest {

    private TotoroBuildHexAdjacentToSettlementSizeFiveRule rule = null;
    private SettlementBoard board = null;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private PieceBoard pieces = null;
    private BuildActionResult result = null;
    private final String errorMessage = "Totoro build hex must be adjacent to a settlment of size >=5";



    @Test
    public void test_ShouldPreventBuildNextToSettlementLessThanFive (){


        //arrange
        pieces = new PieceBoardImpl();
        Location start = new Location(0,0,0);

        pieces.addPiece(new Villager(), start, player1.getId());
        Location previous = start;
        //set up settlment
        for (int i = 0; i<3;++i ){
            pieces.addPiece(new Villager(), previous.getAdjacent(Orientation.getEast()), player1.getId());
            previous = previous.getAdjacent(Orientation.getEast());
        }

        board = new LazySettlementBoard(pieces);
        rule = new TotoroBuildHexAdjacentToSettlementSizeFiveRule(board);

        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(start.getAdjacent(Orientation.getWest()));
        builder.withPlayer(player1);

        //act


        result = rule.applyRule(builder.build());

        //assert

        Assert.assertFalse(result.successful);

        Assert.assertEquals(errorMessage, result.errorMessage);




    }

}