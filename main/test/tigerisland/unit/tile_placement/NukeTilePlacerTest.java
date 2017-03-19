package tigerisland.unit.tile_placement;

import org.junit.Test;
import tigerisland.Board;
import tigerisland.HexBoard;
import tigerisland.tile_placement.*;

public class NukeTilePlacerTest {


    // This test is ONLY temporary
    @Test
    public void test_ShouldCompile() {

        Board board = new HexBoard();

        TilePlacement invalidTilePlacer = new InvalidTilePlacer();
        TilePlacement nukeCoverHexesLevelRule  = new NukeCoverHexesLevelRule();
        TilePlacement nukeHexesMustBeOfDifferentTilesRule = new NukeHexesMustBeOfDifferentTilesRule();

        NukeTilePlacer nukeTilePlacer = new NukeTilePlacer(
                board,
                nukeCoverHexesLevelRule,
                nukeHexesMustBeOfDifferentTilesRule);

        nukeTilePlacer.setNextTilePlacement(invalidTilePlacer);

    }
}
