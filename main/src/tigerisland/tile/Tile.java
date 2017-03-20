package tigerisland.tile;

import tigerisland.hex.Hex;

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
    // TODO
    // needs to be defined by a type
    // left as string for now:: Done by Josh B to fix my dependencies.
    private Orientation orientation;
    private Hex referenceHex;
    private Hex leftHex;
    private Hex rightHex;

   public Tile(){

       id = 5;
       orientation = Orientation.getEast();
       referenceHex = new Hex();
       leftHex = new Hex();
       rightHex = new Hex();
   }

   public Tile(int id, Orientation orientation, Hex referenceHex, Hex leftHex, Hex rightHex){
       this.id = id;
       this.orientation = orientation;
       this.referenceHex = referenceHex;
       this.leftHex = leftHex;
       this.rightHex = rightHex;
   }

   public Tile(int id, Orientation orientation){
       this.id = id;
       this.orientation = orientation;
   }

   public void rotate(){
       // TODO: implement tile rotation
   }


   public int getID(){
       return id;
   }

   public Orientation getOrientation(){
       return orientation;
   }

   public Hex getReferenceHex() { return referenceHex; }

   public Hex getLeftHex(){
       return leftHex;
   }

   public Hex getRightHex(){
       return rightHex;
   }

   //public
}
