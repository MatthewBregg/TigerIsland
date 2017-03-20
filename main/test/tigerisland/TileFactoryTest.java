package tigerisland;

import org.junit.Test;
import tigerisland.tile.Tile;
import tigerisland.tile.TileFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TileFactoryTest {
    private boolean throws_exception(int i) {
        boolean exception_happened = false;
        try {
            TileFactory.getTile(i);
        } catch (Exception e) {
            exception_happened = true;
        }
        return (exception_happened);
    }
    @Test
    public void WhenIndexLess0ThenThrow() throws Exception {
        assert(throws_exception(-1));
    }

    @Test
    public void WhenIndexMore16ThenThrow() throws Exception {
        assert(throws_exception(16));
    }

    @Test
    public void WhenIndexBetween0To16ThenNoThrow() throws Exception {
        for (int i = 0; i != 16; ++i ) {
            assertFalse(throws_exception(i));
        }
    }

    @Test
    public void Indicates16TileTypes(){
        int tileTypes = TileFactory.getTileCombinations();
        assertEquals(tileTypes, 16);
    }

    @Test
    public void When16DrawnThenAllAreUnique() throws Exception {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for ( int i = 0; i != 16; ++ i) {
            Tile tile = TileFactory.getTile(i);
            for ( Tile existing_tile : tiles ) {
                assertFalse(tilesEqual(existing_tile,tile));
            }
            tiles.add(tile);
        }
        assert(tiles.size() == 16);
    }

    boolean tilesEqual(Tile a, Tile b) {
        return ( a.equals(b) );
    }

}