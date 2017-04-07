package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.TilePlacementException;

public interface TilePlacement {

    void placeTile(Tile tile, Location location) throws TilePlacementException;
}
