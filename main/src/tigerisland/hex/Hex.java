package tigerisland.hex;

import tigerisland.terrains.Rocky;
import tigerisland.terrains.Terrain;

/**
 * Created by christinemoore on 3/13/17.
 */

public class Hex {
    private int tileID;
    private Terrain terrain;
    private int level;

    public Hex(int tileID){
        this((int)(Math.random() * 100), (int)(Math.random() * 100), Rocky.getInstance(), 1);
    }

    public Hex(Terrain terrain){
        this((int)(Math.random() * 100), (int)(Math.random() * 100), terrain, 1);
    }

    public Hex(int id, Terrain terrain){
        this(id, (int)(Math.random() * 100), terrain, 1);
    }

    public Hex(int id, int level, Terrain terrain){
        this(id, level, terrain, 1);
    }

    public Hex(int tileID, int settlementID, Terrain terrain, int level){
        this.tileID = tileID;
        this.terrain = terrain;
        this.level = level; // Level should not be set at constructor or will have to be set again when placed on board
    }

    public int getTileID(){
        return tileID;
    }

    public Terrain getTerrain(){
        return terrain;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
