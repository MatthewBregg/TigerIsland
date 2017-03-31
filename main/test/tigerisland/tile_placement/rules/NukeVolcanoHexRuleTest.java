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
import tigerisland.tile_placement.exceptions.NukeVolcanoHexRuleException;
import tigerisland.tile_placement.rules.NukePlacementRule;
import tigerisland.tile_placement.rules.NukeVolcanoHexRule;

import java.util.Map;

public class NukeVolcanoHexRuleTest {

    Map<Location, Hex> hexes;
    Board board;
    NukePlacementRule volcanoHexRule;

    @Before
    public void setup() {

        board = new HexBoard();
        volcanoHexRule = new NukeVolcanoHexRule(board);
    }

    @Test (expected = NukeVolcanoHexRuleException.class)
    public void test_ShouldThrowExceptionWhenVolcanoHexIsNotFound() throws Throwable {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex hexA = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex hexB = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex hexC = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, hexA, hexB, hexC);
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       // Act
       volcanoHexRule.applyRule(hexes);
   }

   @Test
   public void test_ShouldNotThrowExceptionWhenVolcanoHexIsFound() {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex hexA = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
       Hex hexB = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex hexC = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, hexA, hexB, hexC);
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       try {
           // Act
           volcanoHexRule.applyRule(hexes);

       } catch (Throwable throwable) {

           // Assert
           Assert.assertNull(throwable);
       }
   }
}
