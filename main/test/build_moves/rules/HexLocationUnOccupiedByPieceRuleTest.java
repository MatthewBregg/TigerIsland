package build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.rules.HexLocationUnOccupiedByPieceRule;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.PlayerID;

public class HexLocationUnOccupiedByPieceRuleTest {
    PieceBoard pieceBoard;
    HexLocationUnOccupiedByPieceRule hexLocationUnOccupiedByPieceRule;
    Location loc;

    @Before
    public void SetUp() {
        pieceBoard = new PieceBoardImpl();
        hexLocationUnOccupiedByPieceRule = new HexLocationUnOccupiedByPieceRule(pieceBoard);
        loc = new Location(0,0,0);
    }
    @Test
    public void applyRuleWhenOccupied() throws Exception {
        pieceBoard.addPiece(new Villager(),loc, new PlayerID());
        Assert.assertFalse(hexLocationUnOccupiedByPieceRule.applyRule(this.getBuildActionData()).successful);
    }

    private BuildActionData getBuildActionData() {
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(loc);
        return builder.build();
    }

    @Test
    public void applyRuleWhenUnOccupied() throws Exception {
        Assert.assertTrue(hexLocationUnOccupiedByPieceRule.applyRule(this.getBuildActionData()).successful);
    }

}