package tigerisland.tile_placement;

import tigerisland.board.Location;
import tigerisland.tile.Tile;

public interface TilePlacement {

    void placeTile(Tile tile, Location location) throws Throwable;
}
