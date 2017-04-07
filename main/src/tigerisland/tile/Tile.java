package tigerisland.tile;

import tigerisland.hex.Hex;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;

/**
 * Created by christinemoore on 3/2/17.
 * tigerisland.hex.Hex tiles can have 16 different representations
 * of the 3 terrain types
 *
 * tigerisland.terrains.Jungle = J
 * tigerisland.terrains.Lake = L
 * Grass = G
 * tigerisland.terrains.Rocky = R
 *
 *      J    L   G   R
 *
 *  J   JJ   JL  JG  JR
 *s
 *  L   LJ   LL  LG  LR
 *
 *  G   GJ   GL  GG  GR
 *s
 *  R   RJ   RL  RG  RR
 *s
 * could use factory method for this to create each type of hex tile
 * we know the combinations so it would be easy
 */

public class Tile {

    private int id;
    private Orientation orientation;
    private Hex referenceHex;
    private Hex leftHex;
    private Hex rightHex;

   public Tile(){
       this((int)Math.random()*1000000000);
   }

   public Tile(int id,Terrain left, Terrain right){
       this(id,1,left,right);
   }

    public Tile(int id, int level, Terrain left, Terrain right){
        this.id = id;
        this.orientation = Orientation.getEast();
        this.leftHex = new Hex(id,level,left);
        this.rightHex = new Hex(id,level,right);
        this.referenceHex = new Hex(id,level,Volcano.getInstance());
    }


   public Tile(int id){
       this(id,Grassland.getInstance(), Jungle.getInstance());
   }

   public void rotate(){
       orientation = orientation.rotate(60);
   }

   public boolean equals(Tile tile){
       return (this.leftHex.getTerrain() == tile.leftHex.getTerrain()
               && this.rightHex.getTerrain() == tile.rightHex.getTerrain());
   }

   public int getID(){
       return id;
   }

   public Orientation getOrientation(){
       return orientation;
   }

   public void setOrientation(Orientation orientation) {
      this.orientation = orientation;
   }

   public Hex getReferenceHex() {
       return referenceHex;
   }

   public Hex getLeftHex(){
       return leftHex;
   }

   public Hex getRightHex(){
       return rightHex;
   }

   public String getStringOfTerrains()
   {
        return rightHex + "+" + leftHex;
        //Keep in reverse order please. THis is how the server wants it.
   }
}
