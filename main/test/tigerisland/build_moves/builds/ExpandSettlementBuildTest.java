package tigerisland.build_moves.builds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.ExpandSettlementOnHexAction;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Orientation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ExpandSettlementBuildTest {

    private HexBoard hexBoard;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private SettlementExpansionUtility settlementExpansionUtility;
    private ExpandSettlementBuild expandSettlementBuild;

    @Before
    public void setUp() throws Exception {
        hexBoard = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        settlementExpansionUtility = new SettlementExpansionUtility(hexBoard,pieceBoard,settlementBoard);
        expandSettlementBuild = new ExpandSettlementBuild(settlementExpansionUtility,pieceBoard);
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

    private BuildActionResult buildActionResult = null;

    private void runTestWithPlayer(Player p) {
        PrimeBoard();
        Terrain t =  hexBoard.getHex(firstLoc).getTerrain();
        runTestWithPlayer(p,t);
    }
    private void runTestWithPlayer(Player p, Terrain buildActionTerrain) {
        PrimeBoard();
        runTestWithPlayerImpl(p,buildActionTerrain);
    }
    private void runTestWithPlayerImpl(Player p, Terrain buildActionTerrain) {
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withTerrain(buildActionTerrain);
        Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
        builder.withSettlementLocation(settlementLocA);
        pieceBoard.addPiece(new Villager(), settlementLocA, p.getId());
        builder.withPlayer(p);
        buildActionResult = expandSettlementBuild.build(builder.build());
    }

    @Test
    public void test_BuildingASettlement() throws Exception {
        Player player = new Player();
        int villagerCountInit = player.getVillagerCount();
        runTestWithPlayer(player);
        Assert.assertTrue(buildActionResult.errorMessage, buildActionResult.successful);
        Assert.assertEquals(villagerCountInit-4,player.getVillagerCount());
        Settlement s = settlementBoard.getSettlement(firstLoc);
        Assert.assertEquals(3,s.settlementSize());
        Assert.assertEquals(locations_to_place,s.getConnectedLocations());
        assertTrue(buildActionResult.successful);

    }

    @Test
    public void test_NoBuildWhenNoPieces() throws Exception {
        Player player = new Player() {
            @Override
            public int getVillagerCount() {
                return 0;
            }
        };
        int villagerCountInit = player.getVillagerCount();
        runTestWithPlayer(player);
        Assert.assertFalse(buildActionResult.errorMessage, buildActionResult.successful);
        Assert.assertEquals(villagerCountInit,player.getVillagerCount());
        Settlement s = settlementBoard.getSettlement(firstLoc);
        Assert.assertEquals(0,s.settlementSize());
        Assert.assertEquals(0,s.getConnectedLocations().size());

    }

    @Test
    public void test_NoBuildVolcano() throws Exception {
        Player player = new Player();
        int villagerCountInit = player.getVillagerCount();
        Terrain terrain = Volcano.getInstance();
        runTestWithPlayer(player, terrain);
        Assert.assertFalse(buildActionResult.errorMessage, buildActionResult.successful);
        Assert.assertEquals(villagerCountInit,player.getVillagerCount());
        Settlement s = settlementBoard.getSettlement(firstLoc);
        Assert.assertEquals(0,s.settlementSize());
        Assert.assertEquals(0,s.getConnectedLocations().size());

    }

    @Test
    public void test_NoBuildNoSettlement() throws Exception {
        Player player = new Player();
        int villagerCountInit = player.getVillagerCount();

        PrimeBoard();
        Terrain t =  hexBoard.getHex(firstLoc).getTerrain();
        BuildActionData.Builder builder = new BuildActionData.Builder();
        builder.withTerrain(t);
        Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
        builder.withSettlementLocation(settlementLocA);
        builder.withPlayer(player);
        buildActionResult = expandSettlementBuild.build(builder.build());

        Assert.assertFalse(buildActionResult.errorMessage, buildActionResult.successful);
        Assert.assertEquals(villagerCountInit,player.getVillagerCount());
        Settlement s = settlementBoard.getSettlement(firstLoc);
        Assert.assertEquals(0,s.settlementSize());
        Assert.assertEquals(0,s.getConnectedLocations().size());

    }

}