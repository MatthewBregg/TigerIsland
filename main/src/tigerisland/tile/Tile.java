package tigerisland.tile;

import tigerisland.hex.Hex;
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
       this(5, new Hex(), new Hex());
   }

   public Tile(int id, Hex leftHex, Hex rightHex){
       this(id, new Hex(), leftHex, rightHex);
   }

   public Tile(int id, Hex referenceHex, Hex leftHex, Hex rightHex){
       this.id = id;
       this.orientation = new Orientation(0);
       this.referenceHex = referenceHex;
       this.leftHex = leftHex;
       this.rightHex = rightHex;
   }

   public Tile(int id, Orientation orientation){
       this.id = id;
       this.orientation = new Orientation(0);
   }

   public void rotate(){
       orientation.rotate(60);
   }

   public boolean equals(Tile tile){
       return (this.leftHex == tile.leftHex
               && this.rightHex == tile.rightHex
               && this.orientation == tile.orientation
               && this.id == tile.id);
   }

   public int getID(){
       return id;
   }

   public Orientation getOrientation(){
       return orientation;
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

}
