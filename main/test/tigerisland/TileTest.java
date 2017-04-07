package tigerisland; /**
 * Created by christinemoore on 3/14/17.
 */

import org.junit.*;
import tigerisland.hex.Hex;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TileTest {
    public static Tile customTile;

    @BeforeClass
    public static void makeCustomTile(){
        int id = 2;

        customTile = new Tile(id,Rocky.getInstance(), Rocky.getInstance() );
    }

    @Test
    public void makeDefaultTile(){
        Tile defaultTile = new Tile();

        assertTrue(defaultTile instanceof Tile);
    }

    @Test
    public void tileIDSetCorrectly(){
        int tileID = customTile.getID();

        assertTrue(tileID == 2);
    }
    @Test
    public void tileShouldRotateCorrectly(){
        Assert.assertEquals(Orientation.getEast(), customTile.getOrientation() );
        customTile.rotate();
        Assert.assertEquals(Orientation.getNorthEast(), customTile.getOrientation());
        customTile.rotate();
        Assert.assertEquals(Orientation.getNorthWest(), customTile.getOrientation());
        customTile.rotate();
        Assert.assertEquals(Orientation.getWest(), customTile.getOrientation());
        customTile.rotate();
        Assert.assertEquals(Orientation.getSouthWest(), customTile.getOrientation());
        customTile.rotate();
        Assert.assertEquals(Orientation.getSouthEast(), customTile.getOrientation());
        customTile.rotate();
        Assert.assertEquals(Orientation.getEast(), customTile.getOrientation());
    }

    @Test
    public void orientationSetCorrectly(){
        Orientation orientation = customTile.getOrientation();

        assertTrue(orientation.getAngle() == Orientation.EAST);
    }

    @Test
    public void leftTerrainSetCorrectly(){
        Terrain leftTerrain = customTile.getLeftTerrain();

        assertTrue(leftTerrain instanceof Rocky);
    }

    @Test
    public void rightTerrainSetCorrectly(){
        Terrain rightTerrain = customTile.getRightTerrain();

        assertTrue(rightTerrain instanceof Rocky);
    }

    @Test
    public void equalTilesTest(){
        Tile tile1 = new Tile(0);
        Tile tile2 = new Tile(1);
        Tile tile3 = new Tile(2);

        assertTrue(tile1.equals(tile2));
        assertTrue(tile1.equals(tile3));
    }
}
