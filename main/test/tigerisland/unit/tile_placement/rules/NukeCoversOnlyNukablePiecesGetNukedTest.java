package tigerisland.unit.tile_placement.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.PlayerID;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.rules.NukePlacementRule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NukeCoversOnlyNukablePiecesGetNukedTest {
    NukePlacementRule nukePlacementRule = null;
    PieceBoardImpl pieceBoard = new PieceBoardImpl();

    @Before
    public void setUpNukePlacementRule() {
        // Set the nuke placement rule to something here!
    }
    @Test
    public void test_WhenNothingOnBoardNuke() throws Throwable {
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        nukePlacementRule.applyRule(locations);
        // Assert portion is ensuring this passes with no exception!
    }

    @Test
    public void test_WhenTotoroOnHexDoNotNuke() throws Throwable {
        Location totoroLoc = new Location(0,0,0);
        AddTotoroToPieceBoard(totoroLoc);
        Tile t = new Tile();
        Map<Location,Hex> locations = TileUnpacker.getTileHexes(t,totoroLoc);
        nukePlacementRule.applyRule(locations);
        // Assert that an exceptions occurs

    }

    @Test
    public void test_WhenTigerOnHexDoNotNuke() throws Throwable {
        Location tigerLoc = new Location(0,0,0);
        AddTigerToPieceBoard(tigerLoc);
        Tile t = new Tile();
        Map<Location,Hex> locations = TileUnpacker.getTileHexes(t,tigerLoc);
        nukePlacementRule.applyRule(locations);
        // Assert that an exceptions occurs
    }

    @Test
    public void test_WhenAllThreeOnHexDoNotNuke() throws Throwable {
        Tile t = new Tile();
        Map<Location,Hex> locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        Set<Location> keySet = locations.keySet();
        Iterator<Location> locIter = keySet.iterator();
        AddVillagerToPieceBoard(locIter.next());
        AddTotoroToPieceBoard(locIter.next());
        AddTigerToPieceBoard(locIter.next());
        nukePlacementRule.applyRule(locations);
        // Assert that an exceptions occurs
    }

    @Test
    public void test_WhenVillagersOnBoardNuke() throws Throwable {
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        for ( Location loc : locations.keySet() ) {
            AddVillagerToPieceBoard(loc);
        }
        nukePlacementRule.applyRule(locations);
        // Assert portion is ensuring this passes with no exception!
    }

    void AddTotoroToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Totoro(),loc,new PlayerID());
    }
    void AddVillagerToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Villager(),loc,new PlayerID());
    }
    void AddTigerToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Tiger(),loc,new PlayerID());
    }


}
