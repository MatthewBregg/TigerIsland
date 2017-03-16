package tigerisland;

/**
 * Created by christinemoore on 3/13/17.
 */

public class Hex {
    private int tileID;
    private int settlementID;
    private Terrain terrain;
    private int level;

    public Hex(){
        tileID = (int)(Math.random() * 100);
        settlementID = (int)(Math.random() * 100);
        terrain = Rocky.getInstance();
        level = 1;
    }

    public Hex(int tileID, int settlementID, Terrain terrain, int level){
        this.tileID = tileID;
        this.settlementID = settlementID;
        this.terrain = terrain;
        this.level = level;
    }

    public int getTileID(){
        return tileID;
    }

    public int getSettlementID(){
        return settlementID;
    }

    public Terrain getTerrain(){
        return terrain;
    }

    public int getLevel(){
        return level;
    }

}
