package tigerisland;
import java.lang.Exception;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdjacentToBoardTilePlacerTest {
    private HexBoard board;
    private TilePlacer tilePlacer;
    @Before
    public void setUp()  {
        board = new HexBoard();

    }

//    @BeforeClass
//    public void static setUpFactory(){
//
//    }

    @Test
    public void test_AdjacentToBoardTilePlacerShouldPlaceTileOnBoard() {
        //Arrange
        tilePlacer = new AdjacentToBoardTilePlacer();

        Tile tile = new Tile(1, Orientation.getEast());

        Location location = new Location(0,0,0);
        try {
            tilePlacer.placeTile(tile, location, board);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}