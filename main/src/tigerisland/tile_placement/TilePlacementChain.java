package tigerisland.tile_placement;

import tigerisland.Location;
import tigerisland.Tile;

public interface TilePlacementChain {

    void setNextTilePlacement(TilePlacement tilePlacement);

    void nextTilePlacement(Tile tile, Location location);

}
