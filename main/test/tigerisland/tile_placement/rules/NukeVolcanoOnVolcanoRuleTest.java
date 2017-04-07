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
import tigerisland.tile.Orientation;
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
       //int settlementId = -1;
       int hexLevel = 1;



       Tile tile  = new Tile(tileId, 1, Rocky.getInstance(), Grassland.getInstance());
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);

       Tile tile2 = new Tile(tileId+1, Rocky.getInstance(), Grassland.getInstance());
       hexes = TileUnpacker.getTileHexes(tile2, location);
       removeVolcanoFromHexMap(tileId+1);

       // Act
       volcanoOnVolcanoRule.applyRule(hexes);
   }

    @Test (expected = NukeVolcanoOnVolcanoRuleException.class)
    public void test_ShouldThrowExceptionWhenTileDoesNotHaveVolcanoHex() throws Throwable {

       // Arrange
       int tileId = 1;
      // int settlementId = -1;




       Tile tile  = new Tile(tileId, Rocky.getInstance(), Grassland.getInstance());
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);

       Tile tile2 = new Tile(tileId+1, Rocky.getInstance(), Grassland.getInstance());
       hexes = TileUnpacker.getTileHexes(tile2, location);
        removeVolcanoFromHexMap(tileId+1);

       // Act
       volcanoOnVolcanoRule.applyRule(hexes);
   }

   void removeVolcanoFromHexMap(int tId) {
       for ( Map.Entry<Location, Hex> entry : hexes.entrySet()) {
           //Remove the volcano
           if ( entry.getValue().getTerrain() == Volcano.getInstance() ) {
               entry.setValue(new Hex(tId, Grassland.getInstance()));
           }
       }
   }
    @Test
    public void test_ShouldNotThrowExceptionWhenTileVolcanoIsOnBoardVolcano() {

       // Arrange
       int tileId = 1;

       Tile tile  = new Tile(tileId, Rocky.getInstance(), Grassland.getInstance());
       Location location = new Location(0, 0, 0);

       addTileHexesToBoard(tile, location);


       Tile tile2  = new Tile(tileId, Rocky.getInstance(), Grassland.getInstance());
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
