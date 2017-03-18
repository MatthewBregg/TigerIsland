package tigerisland;

import tigerisland.Orientation;

/**
 * Created by josh on 3/16/17.
 */


public interface TilePlacer {
    public void placeTile(Tile tile, Location loc, HexBoard hex);
}




//public OnTopOfTiles implements tigerisland.TilePlacer{
//
//    }
//
//
//
//
//    //case B all of the tiles cover existing hexes
//
//
//    //all covered hexes must be part of 2 or 3 diff tiles
//
//    //none of hexes contain size of 1 stee
//
//}
//
//
//public InvalidPlacementHandler implements tigerisland.TilePlacer{
//
//
//        }