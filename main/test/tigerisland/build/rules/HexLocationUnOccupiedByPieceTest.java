package tigerisland.build.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build.BuildAction;
import tigerisland.build.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.terrains.Terrain;

import static org.junit.Assert.*;

public class HexLocationUnOccupiedByPieceTest {
    PieceBoard pieceBoard;
    HexLocationUnOccupiedByPiece hexLocationUnOccupiedByPiece;
    Location loc;

    @Before
    public void SetUp() {
        pieceBoard = new PieceBoardImpl();
        hexLocationUnOccupiedByPiece = new HexLocationUnOccupiedByPiece(pieceBoard);
        loc = new Location(0,0,0);
    }
    @Test
    public void applyRuleWhenOccupied() throws Exception {
        pieceBoard.addPiece(new Villager(),loc, new PlayerID());
        Assert.assertFalse(hexLocationUnOccupiedByPiece.applyRule(this.getBuildActionData()).successful);
    }

    private BuildActionData getBuildActionData() {
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(loc);
        return builder.build();
    }

    @Test
    public void applyRuleWhenUnOccupied() throws Exception {
        Assert.assertTrue(hexLocationUnOccupiedByPiece.applyRule(this.getBuildActionData()).successful);
    }

}