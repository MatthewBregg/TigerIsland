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
import tigerisland.tile_placement.exceptions.NukeVolcanoOnVolcanoRuleException;
import tigerisland.tile_placement.rules.NukePlacementRule;
import tigerisland.tile_placement.rules.NukeVolcanoHexRule;
import tigerisland.tile_placement.rules.NukeVolcanoOnVolcanoRule;

import java.util.Map;

public class NukeVolcanoOnVolcanoRuleTest {

    Map<Location, Hex> hexes;
    Board board;
    NukePlacementRule volcanoOnVolcanoRule;

    @Before
    public void setup() {

        board = new HexBoard();
        volcanoOnVolcanoRule = new NukeVolcanoOnVolcanoRule(board);
    }

    @Test (expected = NukeVolcanoOnVolcanoRuleException.class)
    public void test_ShouldThrowExceptionWhenTileVolcanoIsNotOnBoardVolcano() throws Throwable {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex volcanoHex = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
       Hex rockyHex = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex grasslandHex = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, volcanoHex, rockyHex, grasslandHex);
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);

       Tile tile2 = new Tile(tileId, rockyHex, volcanoHex, grasslandHex);
       hexes = TileUnpacker.getTileHexes(tile2, location);

       // Act
       volcanoOnVolcanoRule.applyRule(hexes);
   }

    @Test (expected = NukeVolcanoOnVolcanoRuleException.class)
    public void test_ShouldThrowExceptionWhenTileDoesNotHaveVolcanoHex() throws Throwable {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex rockyHex = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex rockyHex2 = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex grasslandHex = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, rockyHex, rockyHex2, grasslandHex);
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);

       Tile tile2 = new Tile(tileId, rockyHex2, rockyHex, grasslandHex);
       hexes = TileUnpacker.getTileHexes(tile2, location);

       // Act
       volcanoOnVolcanoRule.applyRule(hexes);
   }

    @Test
    public void test_ShouldNotThrowExceptionWhenTileVolcanoIsOnBoardVolcano() {

       // Arrange
       int tileId = 1;
       int settlementId = -1;
       int hexLevel = 1;

       Hex volcanoHex = new Hex(tileId, settlementId, Volcano.getInstance(), hexLevel);
       Hex rockyHex = new Hex(tileId, settlementId, Rocky.getInstance(), hexLevel);
       Hex grasslandHex = new Hex(tileId, settlementId, Grassland.getInstance(), hexLevel);

       Tile tile  = new Tile(tileId, volcanoHex, rockyHex, grasslandHex);
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);


       Tile tile2  = new Tile(tileId, volcanoHex, rockyHex, grasslandHex);
       hexes = TileUnpacker.getTileHexes(tile2, location);

       try {
           // Act
           volcanoOnVolcanoRule.applyRule(hexes);

       } catch (Throwable throwable) {

           // Assert
           Assert.assertNull(throwable);
       }
   }

    private void addTileHexesToBoard(Tile tile, Location location) {

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });
   }

}
