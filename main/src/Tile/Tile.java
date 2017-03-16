package Tile;

import Terrain.Terrain;
import Terrain.Grassland;
import Terrain.Rocky;

/**
 * Created by christinemoore on 3/2/17.
 * Hex.Hex tiles can have 16 different representations
 * of the 3 terrain types
 *
 * Terrain.Jungle = J
 * Terrain.Lake = L
 * Grass = G
 * Terrain.Rocky = R
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
    // left as string for now
    private String orientation;
    private Terrain leftTerrain;
    private Terrain rightTerrain;

   public Tile(){
       id = 5;
       orientation = "left";
       leftTerrain = Rocky.getInstance();
       rightTerrain = Grassland.getInstance();
   }

   public Tile(int id, String orientation, Terrain leftTerrain, Terrain rightTerrain){
       this.id = id;
       this.orientation = orientation;
       this.leftTerrain = leftTerrain;
       this.rightTerrain = rightTerrain;
   }

   public int getID(){
       return id;
   }

   public String getOrientation(){
       return orientation;
   }

   public Terrain getLeftTerrain(){
       return leftTerrain;
   }

   public Terrain getRightTerrain(){
       return rightTerrain;
   }

   //public
}
