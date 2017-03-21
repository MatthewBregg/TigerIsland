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
        Hex leftHex = new Hex();
        Hex rightHex = new Hex();

        customTile = new Tile(id, leftHex, rightHex);
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
    public void orientationSetCorrectly(){
        Orientation orientation = customTile.getOrientation();

        assertTrue(orientation.getAngle() == Orientation.EAST);
    }

    @Test
    public void leftTerrainSetCorrectly(){
        Terrain leftTerrain = customTile.getLeftHex().getTerrain();

        assertTrue(leftTerrain instanceof Rocky);
    }

    @Test
    public void rightTerrainSetCorrectly(){
        Terrain rightTerrain = customTile.getRightHex().getTerrain();

        assertTrue(rightTerrain instanceof Rocky);
    }

    @Test
    public void equalTilesTest(){
        Tile tile1 = new Tile(0, new Hex(), new Hex());
        Tile tile2 = new Tile(0, new Hex(), new Hex());
        Tile tile3 = new Tile(1, new Hex(), new Hex());

        assertTrue(tile1.equals(tile2));
        assertTrue(tile1.equals(tile3));
    }
}
