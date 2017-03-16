package tigerisland; /**
 * Created by christinemoore on 3/14/17.
 */

import org.junit.*;
import tigerisland.Hex;
import tigerisland.Rocky;
import tigerisland.Terrain;


public class HexTest {
    public static Hex customHex;

    @BeforeClass
    public static void makeCustomHex(){
        int tileID = 5;
        int settlementID = 6;
        Terrain terrain = Rocky.getInstance();
        int level = 1;

        customHex = new Hex(tileID, settlementID, terrain, level);
    }

    @Test
    public void makeDefaultTile(){
        Hex defaultHex = new Hex();

        Assert.assertTrue(defaultHex instanceof Hex);
    }

    @Test
    public void tileIDSetCorrectly(){
        int tileID = customHex.getTileID();

        Assert.assertTrue(tileID == 5);
    }

    @Test
    public void settlementIDSetCorrectly(){
        int settleID = customHex.getSettlementID();

        Assert.assertTrue(settleID == 6);
    }

    @Test
    public void terrainSetCorrectly(){
        Terrain setTerrain = customHex.getTerrain();

        Assert.assertTrue(setTerrain instanceof Rocky);
    }


    @Test
    public void levelSetCorrectly(){
        int level = 1;

        Assert.assertTrue(level == customHex.getLevel());
    }
}
