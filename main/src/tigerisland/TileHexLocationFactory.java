package tigerisland;


import java.util.HashMap;
import java.util.List;

public interface TileHexLocationFactory {
    enum NonVolcanoTile{LEFT, RIGHT}
    HashMap<NonVolcanoTile, Location > getNonVolcanoHexLocations(Location volcanoHexLocation, Orientation tileOrientation);
}

