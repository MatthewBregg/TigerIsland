package tigerisland.build_moves;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Jungle;
import tigerisland.tile.Orientation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SettlementExpansionUtilityTest {
    private HexBoard hexBoard;
    private PieceBoard pieceBoard;
    private SettlementExpansionUtility settlementExpansionUtility;
    private SettlementBoard settlementBoard;

    @Before
    public void setUp() throws Exception {
        hexBoard = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        settlementExpansionUtility = new SettlementExpansionUtility(hexBoard,pieceBoard,settlementBoard);
    }

    Location firstLoc = null;
    Location lastLoc = null;
    Location middle = null;
    Set<Location> locations_to_place = null;
    private void PrimeBoard() {
        locations_to_place = new HashSet<Location>();
        Location primingLoc = new Location(0,0,0);
        firstLoc = primingLoc.getAdjacent(Orientation.getEast());
        for ( int i = 0; i != 11; ++i ) {
            primingLoc = primingLoc.getAdjacent(Orientation.getEast());
            if ( i == 5 ) {
                middle = primingLoc;
            }
            locations_to_place.add(primingLoc);
        }

        for ( Location loc : locations_to_place ) {
            hexBoard.placeHex(loc,new Hex(Jungle.getInstance()));
        }
        lastLoc = primingLoc;
    }
    @Test
    public void test_getConnectedUnOccupiedHexes() throws Exception {
        PrimeBoard();
        Set<Location> connected_locs = settlementExpansionUtility.getConnectedUnOccupiedHexesOfSameTerrain(lastLoc);
        Assert.assertTrue(connected_locs.containsAll(locations_to_place) && locations_to_place.containsAll(connected_locs));

    }

    @Test
    public void test_getConnectedUnOccupiedHexesWithPieceBlocking() throws Exception {
        PrimeBoard();
        pieceBoard.addPiece(new Villager(),middle,new PlayerID());
        Set<Location> connected_locs = settlementExpansionUtility.getConnectedUnOccupiedHexesOfSameTerrain(firstLoc);
        Assert.assertTrue(connected_locs.size() == 5);
        for ( int i = 5; i != 12; ++ i ) {
            locations_to_place.remove(middle);
            middle = middle.getAdjacent(Orientation.getEast());
        }
        Assert.assertTrue(connected_locs.containsAll(locations_to_place) && locations_to_place.containsAll(connected_locs));

    }



    @Test
    public void test_FindExpandableUnoccupiedHexesThatAlignASettlement() throws Exception {
        PrimeBoard();
        Player p = new Player();
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withHexLocation(firstLoc);
        Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
        Location settlementLocB = settlementLocA.getAdjacent(Orientation.getEast());
        builder.withSettlementLocation(settlementLocA);
        pieceBoard.addPiece(new Villager(), settlementLocA, p.getId());
        pieceBoard.addPiece(new Villager(), settlementLocB, p.getId());
        builder.withPlayer(p);
        Set<Location> connected_locs = settlementExpansionUtility.getExpandableHexes(builder.build());
        locations_to_place.removeAll(Arrays.asList(new Location[]{settlementLocA, settlementLocB}));
        Assert.assertTrue(connected_locs.containsAll(locations_to_place) && locations_to_place.containsAll(connected_locs));
    }

}