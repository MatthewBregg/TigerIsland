package tigerisland.build.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.ExpandSettlementOnHexAction;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Jungle;
import tigerisland.tile.Orientation;

import java.util.HashSet;
import java.util.Set;

public class ExpandSettlementOnHexActionTest {
    private HexBoard hexBoard;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private SettlementExpansionUtility settlementExpansionUtility;
    private ExpandSettlementOnHexAction expandSettlementOnHexAction;

    @Before
    public void setUp() throws Exception {
        hexBoard = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        settlementExpansionUtility = new SettlementExpansionUtility(hexBoard,pieceBoard,settlementBoard);
        expandSettlementOnHexAction = new ExpandSettlementOnHexAction(pieceBoard,settlementExpansionUtility);
    }

    Location firstLoc = null;
    Set<Location> locations_to_place = null;
    private void PrimeBoard() {
        locations_to_place = null;
        locations_to_place = new HashSet<Location>();
        Location primingLoc = new Location(0,0,0);
        firstLoc = primingLoc.getAdjacent(Orientation.getEast());
        for ( int i = 0; i != 3; ++i ) {
            primingLoc = primingLoc.getAdjacent(Orientation.getEast());
            locations_to_place.add(primingLoc);
        }
        int i = 1;
        for ( Location loc : locations_to_place ) {
            Hex h = new Hex(Jungle.getInstance());
            h.setLevel(i);
            ++i;
            hexBoard.placeHex(loc,h);
        }
    }


    private void runTestWithPlayer(Player p) {
        PrimeBoard();
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(firstLoc);
        Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
        builder.withSettlementLocation(settlementLocA);
        pieceBoard.addPiece(new Villager(), settlementLocA, p.getId());
        builder.withPlayer(p);
        expandSettlementOnHexAction.applyAction(builder.build());
    }

    @Test
    public void test_BuildingASettlement() throws Exception {
        Player player = new Player();
        int villagerCountInit = player.getVillagerCount();
        runTestWithPlayer(player);
        Assert.assertEquals(villagerCountInit-4,player.getVillagerCount());
        Settlement s = settlementBoard.getSettlement(firstLoc);
        Assert.assertEquals(3,s.settlementSize());
        Assert.assertEquals(locations_to_place,s.getConnectedLocations());

    }




}