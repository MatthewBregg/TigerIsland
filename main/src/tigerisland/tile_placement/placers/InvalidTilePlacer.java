package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;
import tigerisland.tile_placement.exceptions.TilePlacementException;

public class InvalidTilePlacer implements TilePlacement{


    @Override
    public void placeTile(Tile tile, Location location) throws TilePlacementException {
        throw new InvalidTilePlacementException();
    }

}
