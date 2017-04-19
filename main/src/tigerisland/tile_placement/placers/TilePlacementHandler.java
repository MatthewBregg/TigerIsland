package tigerisland.tile_placement.placers;

import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.settlement.Settlement;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.TilePlacementException;

import java.util.Map;
import java.util.Set;

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
