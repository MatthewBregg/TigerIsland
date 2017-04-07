package tigerisland.tile_placement.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.exceptions.NukeHexesOfDifferentTilesRuleException;
import tigerisland.tile_placement.rules.NukeHexesOfDifferentTilesRule;
import tigerisland.tile_placement.rules.NukePlacementRule;

import java.util.Map;

public class NukeHexesOfDifferentTilesRuleTest {

    Map<Location, Hex> hexes;
    Board board;
    NukePlacementRule differentTilesRule;

    @Before
    public void setup() {

        board = new HexBoard();
        differentTilesRule = new NukeHexesOfDifferentTilesRule(board);
    }

    @Test (expected = NukeHexesOfDifferentTilesRuleException.class)
    public void test_ShouldThrowExceptionWhenHexesBelongToOneTile() throws Throwable {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex hexA = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
       Hex hexB = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex hexC = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, hexLevel, Rocky.getInstance(), Grassland.getInstance());
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       // Act
       differentTilesRule.applyRule(hexes);
   }

   @Test
    public void test_ShouldNotThrowExceptionWhenHexesBelongToTwoTiles() {

       // Arrange
       int tileId = 1;
       int tileId2 = 2;

       //int settlementId = -1;
       int hexLevel = 1;

      // Hex hexA = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
       //Hex hexB = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
      // Hex hexC = new Hex(tileId2, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = null; //new Tile(tileId, hexA, hexB, hexC);
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       try {
           // Act
           differentTilesRule.applyRule(hexes);

       } catch (Throwable throwable) {

           // Assert
           Assert.assertNull(throwable);
       }
   }

   @Test
    public void test_ShouldNotThrowExceptionWhenHexesBelongToThreeTiles() {

       // Arrange
       int tileId = 1;
       int tileId2 = 2;
       int tileId3 = 3;

      // int settlementId = -1;
       int hexLevel = 1;

      // Hex hexA = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
      // Hex hexB = new Hex(tileId2, settlementId, Rocky.getInstance(), hexLevel);
      // Hex hexC = new Hex(tileId3, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = null; //new Tile(tileId, hexA, hexB, hexC);
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       try {
           // Act
           differentTilesRule.applyRule(hexes);

       } catch (Throwable throwable) {

           // Assert
           Assert.assertNull(throwable);
       }
   }

}
