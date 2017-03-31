package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.tile.Tile;

public interface TilePlacementChain {

    void setNextTilePlacement(TilePlacement tilePlacement);

    void nextTilePlacement(Tile tile, Location location) throws Exception;

}
