package tigerisland.tile;


import tigerisland.board.Location;
import tigerisland.hex.Hex;
import java.util.HashMap;
import java.util.Map;

public class TileUnpacker {
// From Orientation.java
//  EAST=0;
//  NORTHEAST=60;
//  NORTHWEST=120;
//  WEST=180;
//  SOUTHWEST=240;
//  SOUTHEAST=300;

    private TileUnpacker() {}

    public static Map<Location, Hex> getTileHexes(Tile tile, Location referenceHexLocation)  {

        Orientation tileOrientation = tile.getOrientation();
        HashMap<Location, Hex> nonVolcanoHexes = new HashMap<>();
        nonVolcanoHexes.put(referenceHexLocation, tile.getReferenceHex());

        switch(tileOrientation.getAngle()){
            case Orientation.EAST:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getSouthWest()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getSouthEast()), tile.getRightHex());
                return nonVolcanoHexes;
            case Orientation.NORTHEAST:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getSouthEast()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getEast()), tile.getRightHex());
                return nonVolcanoHexes;
            case Orientation.NORTHWEST:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getEast()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getNorthEast()), tile.getRightHex());
                return nonVolcanoHexes;
            case Orientation.WEST:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getNorthEast()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getNorthWest()), tile.getRightHex());
                return nonVolcanoHexes;
            case Orientation.SOUTHWEST:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getNorthWest()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getWest()), tile.getRightHex());
                return nonVolcanoHexes;
            // Orientation.SOUTHEAST
            default:
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getWest()), tile.getLeftHex());
                nonVolcanoHexes.put(referenceHexLocation.getAdjacent(Orientation.getSouthWest()), tile.getRightHex());
                return nonVolcanoHexes;
        }
    }
}
