package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;

public class InvalidTilePlacer implements TilePlacement{


    @Override
    public void placeTile(Tile tile, Location location) throws Exception{
        throw new InvalidTilePlacementException();
    }

}