package tigerisland.tile_placement;

import tigerisland.Location;
import tigerisland.Tile;

public class TilePlacementManager implements TilePlacement {

    TilePlacement tilePlacement;

    public TilePlacementManager(TilePlacement tilePlacement) {
        this.tilePlacement = tilePlacement;
    }

    @Override
    public void placeTile(Tile tile, Location location) {
        try {
            tilePlacement.placeTile(tile, location);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
