package tigerisland;


import java.util.HashMap;

public class TileHexFinder {
// From Orientation.java
//  EAST=0;
//  NORTHEAST=60;
//  NORTHWEST=120;
//  WEST=180;
//  SOUTHWEST=240;
//  SOUTHEAST=300;

    public HashMap<NonVolcanoHex, Location> getNonVolcanoHexLocations(Location volcanoHexLocation, Orientation tileOrientation)  {

        HashMap<NonVolcanoHex, Location> nonVolcanoHexes = new HashMap<>();

        switch(tileOrientation.getAngle()){
            case Orientation.EAST:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getSouthWest()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthEast()));
                return nonVolcanoHexes;
            case Orientation.NORTHEAST:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getWest()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthWest()));
                return nonVolcanoHexes;
            case Orientation.NORTHWEST:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getNorthWest()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getWest()));
                return nonVolcanoHexes;
            case Orientation.WEST:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getNorthEast()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getNorthWest()));
                return nonVolcanoHexes;
            case Orientation.SOUTHWEST:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getEast()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getNorthEast()));
                return nonVolcanoHexes;
            // Orientation.SOUTHEAST
            default:
                nonVolcanoHexes.put(NonVolcanoHex.LEFT, volcanoHexLocation.getAdjacent(Orientation.getSouthEast()));
                nonVolcanoHexes.put(NonVolcanoHex.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getEast()));
                return nonVolcanoHexes;
        }
    }
}
