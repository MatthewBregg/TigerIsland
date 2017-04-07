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
        int level = 1;

        Orientation tileOrientation = tile.getOrientation();
        Location left = referenceHexLocation.getAdjacent(tileOrientation.rotate(-120));
        Location right = referenceHexLocation.getAdjacent(tileOrientation.rotate(-60));

        HashMap<Location, Hex> nonVolcanoHexes = new HashMap<>();
        int tId = tile.getID();
        nonVolcanoHexes.put(referenceHexLocation, new Hex(tId, level, tile.getReferenceTerrain()));
        nonVolcanoHexes.put(left, new Hex(tId,level, tile.getLeftTerrain()));
        nonVolcanoHexes.put(right, new Hex(tId, level, tile.getRightTerrain()));

        return nonVolcanoHexes;
    }
}
