package tigerisland.tile_placement.rules;

import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile_placement.exceptions.TilePlacementException;

import java.util.Map;

public interface NukePlacementRule {

    public void applyRule(Map<Location, Hex> hexes) throws TilePlacementException;
}
