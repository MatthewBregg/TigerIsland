package build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.rules.SettlementLocationAdjacentToHexLocationRule;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Orientation;

public class SettlementLocationAdjacentToHexLocationRuleTest {
    PieceBoard pieceBoard;
    SettlementLocationAdjacentToHexLocationRule settlementLocationAdjacentToHexLocationRule;
    Location hexLoc;
    Location settlementLoc;
    SettlementBoard settlementBoard;
    Player player;
    private Location inBetweenLoc;

    @Before
    public void SetUp() {
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        hexLoc = new Location(0,0,0);
        inBetweenLoc = hexLoc.getAdjacent(Orientation.getEast());
        settlementLoc = inBetweenLoc.getAdjacent(Orientation.getEast());
        settlementLocationAdjacentToHexLocationRule = new SettlementLocationAdjacentToHexLocationRule(settlementBoard);
        player = new Player();
    }
    @Test
    public void IfNoSettlementThenFail() throws Exception {
        Assert.assertFalse(settlementLocationAdjacentToHexLocationRule.applyRule(this.createBuildAction()).successful);
    }

    @Test
    public void IfSettlementThenSucceed() throws Exception {
        pieceBoard.addPiece(new Villager(), settlementLoc, player.getId());
        pieceBoard.addPiece(new Villager(), inBetweenLoc, player.getId());
        Assert.assertTrue(settlementLocationAdjacentToHexLocationRule.applyRule(this.createBuildAction()).successful);
    }


    private BuildActionData createBuildAction() {
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(hexLoc);
        builder.withSettlementLocation(settlementLoc);
        builder.withPlayer(player);
        return builder.build();
    }

}