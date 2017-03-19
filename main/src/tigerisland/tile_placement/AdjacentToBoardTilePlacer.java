package tigerisland.tile_placement;

import tigerisland.Location;
import tigerisland.Tile;

public class AdjacentToBoardTilePlacer implements TilePlacement, TilePlacementChain{


    TilePlacement tilePlacement;

    @Override
    public void placeTile(Tile tile, Location location) {
        nextTilePlacement(tile, location);
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
       this.tilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) {
        if (tilePlacement != null)
            tilePlacement.placeTile(tile, location);
    }

}
