package tigerisland.unit.tile_placement;

import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.tile_placement.*;
import tigerisland.tile_placement.rules.NukeCoverHexesLevelRule;
import tigerisland.tile_placement.rules.NukeHexesMustBeOfDifferentTilesRule;

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
