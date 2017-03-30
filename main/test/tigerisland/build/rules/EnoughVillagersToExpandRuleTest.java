package tigerisland.build.rules;

import org.junit.Before;
import org.junit.Test;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.EnoughVillagersToExpandRule;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Jungle;
import tigerisland.tile.Orientation;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnoughVillagersToExpandRuleTest {
    private HexBoard hexBoard;
    private PieceBoard pieceBoard;
    private SettlementExpansionUtility settlementExpansionUtility;
    private SettlementBoard settlementBoard;
    private EnoughVillagersToExpandRule enoughVillagersToExpandRule;

    @Before
    public void setUp() throws Exception {
        hexBoard = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        settlementExpansionUtility = new SettlementExpansionUtility(hexBoard,pieceBoard,settlementBoard);
        enoughVillagersToExpandRule = new EnoughVillagersToExpandRule(settlementExpansionUtility);
    }

    Location firstLoc = null;
    private void PrimeBoard() {
        Set<Location> locations_to_place = null;
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


    private BuildActionResult runTestWithPlayer(Player p) {
        PrimeBoard();
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(firstLoc);
        Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
        builder.withSettlementLocation(settlementLocA);
        pieceBoard.addPiece(new Villager(), settlementLocA, p.getId());
        builder.withPlayer(p);
        return enoughVillagersToExpandRule.applyRule(builder.build());
    }

    @Test
    public void test_PlayerHasEnoughVillagers() throws Exception {
        Player player = new Player() {
            @Override
            public int getVillagerCount() {
                return 4;
            }
        };
        BuildActionResult result = runTestWithPlayer(player);
        assertTrue(result.successful);
    }


    @Test
    public void test_PlayerDoesNotHaveEnoughVillagers() throws Exception {
        Player player = new Player() {
            @Override
            public int getVillagerCount() {
                return 0;
            }
        };
        BuildActionResult result = runTestWithPlayer(player);
        assertFalse(result.successful);
    }

}