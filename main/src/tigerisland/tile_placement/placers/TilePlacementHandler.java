package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.TilePlacementException;

public class TilePlacementHandler implements TilePlacement {

    TilePlacement tilePlacement;

    public TilePlacementHandler(TilePlacement tilePlacement) {
        this.tilePlacement = tilePlacement;
    }

    @Override
    public void placeTile(Tile tile, Location location) {
        try {
            tilePlacement.placeTile(tile, location);
        } catch (TilePlacementException throwable) {
            throwable.printStackTrace();
        }
    }

}
