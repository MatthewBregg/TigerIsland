package tigerisland.tile_placement;

import tigerisland.Location;
import tigerisland.Tile;

public class InvalidTilePlacer implements TilePlacement{


    @Override
    public void placeTile(Tile tile, Location location) throws Throwable{
        throw new InvalidTilePlacementException();
    }

}
