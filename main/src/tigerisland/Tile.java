package tigerisland;

/**
 * Created by christinemoore on 3/2/17.
 * tigerisland.Hex tiles can have 16 different representations
 * of the 3 terrain types
 *
 * tigerisland.Jungle = J
 * tigerisland.Lake = L
 * Grass = G
 * tigerisland.Rocky = R
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
    private Terrain leftTerrain;
    private Terrain rightTerrain;

   public Tile(){

       id = 5;
       orientation = Orientation.getEast();
       leftTerrain = Rocky.getInstance();
       rightTerrain = Grassland.getInstance();
   }

   public Tile(int id, Orientation orientation, Terrain leftTerrain, Terrain rightTerrain){
       this.id = id;
       this.orientation = orientation;
       this.leftTerrain = leftTerrain;
       this.rightTerrain = rightTerrain;
   }

   public int getID(){
       return id;
   }

   public Orientation getOrientation(){
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
