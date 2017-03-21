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

        Assert.assertTrue(defaultTile instanceof Tile);
    }

    @Test
    public void tileIDSetCorrectly(){
        int tileID = customTile.getID();

        Assert.assertTrue(tileID == 2);
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

        Assert.assertTrue(orientation.getAngle() == Orientation.EAST);
    }

    @Test
    public void leftTerrainSetCorrectly(){
        Terrain leftTerrain = customTile.getLeftHex().getTerrain();

        Assert.assertTrue(leftTerrain instanceof Rocky);
    }

    @Test
    public void rightTerrainSetCorrectly(){
        Terrain rightTerrain = customTile.getRightHex().getTerrain();

        Assert.assertTrue(rightTerrain instanceof Rocky);
    }
}
