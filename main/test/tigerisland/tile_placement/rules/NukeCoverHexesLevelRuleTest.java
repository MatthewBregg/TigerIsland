package tigerisland.tile_placement.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.exceptions.NukeCoverHexesLevelRuleException;
import tigerisland.tile_placement.rules.NukeCoverHexesLevelRule;
import tigerisland.tile_placement.rules.NukePlacementRule;

import java.util.Map;

public class NukeCoverHexesLevelRuleTest {

    Map<Location, Hex> hexes;
    Board board;
    NukePlacementRule hexesLevelRule;

    @Before
    public void setup() {

        board = new HexBoard();
        hexesLevelRule = new NukeCoverHexesLevelRule(board);
    }

    @Test (expected = NukeCoverHexesLevelRuleException.class)
    public void test_ShouldThrowExceptionWhenABoardHexHasDifferentLevel() throws Throwable {

       // Arrange
       Tile tile  = new Tile(0, 1, Rocky.getInstance(), Lake.getInstance());
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

       // Act
       hexesLevelRule.applyRule(hexes);
   }

    @Test
    public void test_ShouldNotThrowExceptionWhenBoardHexesHaveTheSameLevel() {

       // Arrange

       Tile tile  = new Tile(0);
       Location location = new Location(0, 0, 0);

       hexes = TileUnpacker.getTileHexes(tile, location);

       hexes.forEach( (loc, hex) -> {
          board.placeHex(loc, hex);
       });

        try {
            // Act
            hexesLevelRule.applyRule(hexes);

        } catch (Throwable throwable) {

            // Assert
            Assert.assertNull(throwable);
        }
   }
}
