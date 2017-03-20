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
        Orientation orientation = Orientation.getNorthEast();
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
    public void orientationSetCorrectly(){
        Orientation orientation = customTile.getOrientation();

        Assert.assertTrue(orientation.getAngle() == Orientation.NORTHEAST);
    }

    @Test
    public void leftTerrainSetCorrectly(){
        Terrain leftTerrain = customTile.getLeftHex().getTerrain();

        Assert.assertTrue(leftTerrain instanceof Rocky);
    }

    @Test
    public void rightTerrainSetCorrectly(){
        Terrain rightTerrain = customTile.getRightHex().getTerrain();

        Assert.assertTrue(rightTerrain instanceof Grassland);
    }
}
