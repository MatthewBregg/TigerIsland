package tigerisland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileHexLocationFactoryImp implements TileHexLocationFactory {


// From Orientation.java EAST=0;
//  NORTHEAST=60;
//  NORTHWEST=120;
//  WEST=180;
//  SOUTHWEST=240;
//  SOUTHEAST=300;


    public HashMap<NonVolcanoTile, Location> getNonVolcanoHexLocations(Location volcanoHexLocation, Orientation tileOrientation) throws Exception {

        HashMap<NonVolcanoTile, Location> nonVolcanoHexes = new HashMap<>();

        switch(tileOrientation.getAngle()){
            case Orientation.EAST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getSouthWest()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthEast()));
                return nonVolcanoHexes;
            case Orientation.NORTHEAST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getWest()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthWest()));
                return nonVolcanoHexes;
            case Orientation.NORTHWEST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getNorthWest()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getWest()));
                return nonVolcanoHexes;
            case Orientation.WEST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getNorthEast()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getNorthWest()));
                return nonVolcanoHexes;
            case Orientation.SOUTHWEST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getEast()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getNorthEast()));
                return nonVolcanoHexes;
            case Orientation.SOUTHEAST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getEast()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getNorthEast()));
            default:
                throw new Exception("Unknown Orientation Detected");
        }
    }
}
