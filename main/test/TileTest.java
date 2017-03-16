/**
 * Created by christinemoore on 3/14/17.
 */

import org.junit.*;


public class TileTest {
    public static Tile customTile;

    @BeforeClass
    public static void makeCustomTile(){
        int id = 2;
        String orientation = "30";
        Terrain leftTerrain = Rocky.getInstance();
        Terrain rightTerrain = Grassland.getInstance();

        customTile = new Tile(id, orientation, leftTerrain, rightTerrain);
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
        String orientation = customTile.getOrientation();

        Assert.assertTrue(orientation == "30");
    }

    @Test
    public void leftTerrainSetCorrectly(){
        Terrain leftTerrain = customTile.getLeftTerrain();

        Assert.assertTrue(leftTerrain instanceof Rocky);
    }

    @Test
    public void rightTerrainSetCorrectly(){
        Terrain rightTerrain = customTile.getRightTerrain();

        Assert.assertTrue(rightTerrain instanceof Grassland);
    }
}
