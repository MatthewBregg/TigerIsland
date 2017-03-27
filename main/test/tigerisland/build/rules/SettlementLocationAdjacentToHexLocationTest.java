package tigerisland.build.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Orientation;

import static org.junit.Assert.*;

public class SettlementLocationAdjacentToHexLocationTest {
    PieceBoard pieceBoard;
    SettlementLocationAdjacentToHexLocation settlementLocationAdjacentToHexLocation;
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
        settlementLocationAdjacentToHexLocation = new SettlementLocationAdjacentToHexLocation(settlementBoard);
        player = new Player();
    }
    @Test
    public void IfNoSettlementThenFail() throws Exception {
        Assert.assertFalse(settlementLocationAdjacentToHexLocation.applyRule(this.createBuildAction()).successful);
    }

    @Test
    public void IfSettlementThenSucceed() throws Exception {
        pieceBoard.addPiece(new Villager(), settlementLoc, player.getId());
        pieceBoard.addPiece(new Villager(), inBetweenLoc, player.getId());
        Assert.assertTrue(settlementLocationAdjacentToHexLocation.applyRule(this.createBuildAction()).successful);
    }


    private BuildActionData createBuildAction() {
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(hexLoc);
        builder.withSettlementLocation(settlementLoc);
        builder.withPlayer(player);
        return builder.build();
    }

}