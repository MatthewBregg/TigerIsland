package tigerisland.unit.tile_placement.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Tiger;
import tigerisland.piece.Totoro;
import tigerisland.piece.Villager;
import tigerisland.player.PlayerID;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.exceptions.NukeSettlementSizeException;
import tigerisland.tile_placement.rules.NukePlacementRule;
import tigerisland.tile_placement.rules.NukeSettlementSizeGreaterThanOneRule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NukeDoesNotEradicateASettlement {

    NukePlacementRule nukePlacementRule = null;
    PieceBoardImpl pieceBoard = null;
    SettlementBoard settlementBoard = null;


    @Before
    public void setUpNukePlacementRule() {
        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);
        nukePlacementRule = new NukeSettlementSizeGreaterThanOneRule(settlementBoard);
    }

    void CreateSettlement(Location... locations) {
        for ( Location loc : locations ) {
            AddVillagerToPieceBoard(loc);
        }
    }
    void CreateSettlementOnePlayer(Location... locations) {
        PlayerID playerID = new PlayerID();
        for ( Location loc : locations ) {
            AddVillagerToPieceBoardOnePlayer(loc, playerID);
        }
    }

    @Test (expected = NukeSettlementSizeException.class)
    public void test_DoNotNukeSettlementSizeOne() throws Throwable{
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        CreateSettlement(new Location(0,0,0));
        nukePlacementRule.applyRule(locations);
    }

    @Test (expected  = NukeSettlementSizeException.class )
    public void test_DoNotNukeSettlementCompletelyCovered() throws Throwable{
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        for (Location loc : locations.keySet()) {
            AddVillagerToPieceBoard(loc);
        }
        CreateSettlement(locations.keySet().stream().toArray(Location[]::new));
        nukePlacementRule.applyRule(locations);
    }

    @Test
    public void test_DoNukeWhenNoSettlement() throws Throwable{
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        boolean exceptionThrow = false;
        try{
            nukePlacementRule.applyRule(locations);
        }
        catch(Exception e){
            exceptionThrow = true;
        }
        Assert.assertFalse(exceptionThrow);

        //TODO check if piece actually placed
    }

    @Test
    public void test_DoNukeWhenSettlementFarOff() throws Throwable{
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));
        CreateSettlement(new Location(42,42,42*-2));
        boolean exceptionThrow = false;
        try{
            nukePlacementRule.applyRule(locations);
        }
        catch(Exception e){
            exceptionThrow = true;
        }
        Assert.assertFalse(exceptionThrow);
    }

    @Test
    public void test_DoNukePartiallyCoveredSettlement() throws Throwable {
        Map<Location,Hex> locations;
        Tile t = new Tile();
        locations = TileUnpacker.getTileHexes(t,new Location(0,0,0));

        Location currLoc = new Location(0,0,0);
        Set<Location> settlementLocation = new HashSet<Location>();
        settlementLocation.addAll(locations.keySet());
        for ( int i = 0; i != 10; ++i ) {
            currLoc = currLoc.getAdjacent(Orientation.getEast());
            settlementLocation.add(currLoc);
        }
        CreateSettlementOnePlayer(settlementLocation.stream().toArray(Location[]::new));
        boolean exceptionThrow = false;
        try{
            nukePlacementRule.applyRule(locations);
        }
        catch(Exception e){
            exceptionThrow = true;
        }
        Assert.assertFalse(exceptionThrow);
    }


    void AddTotoroToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Totoro(),loc,new PlayerID());
    }
    void AddVillagerToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Villager(),loc,new PlayerID());
    }
    void AddVillagerToPieceBoardOnePlayer(Location loc, PlayerID playerID){pieceBoard.addPiece(new Villager(), loc, playerID);}
    void AddTigerToPieceBoard(Location loc) {
        pieceBoard.addPiece(new Tiger(),loc,new PlayerID());
    }
}
