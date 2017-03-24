package tigerisland.tile_placement.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile_placement.exceptions.NukeVolcanoHexRuleException;

import java.util.Map;
import java.util.Set;

public class NukeVolcanoHexRule implements NukePlacementRule{

    Board board;

    public NukeVolcanoHexRule(Board board) {
        this.board = board;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws Exception {

        if ( !isThereAVolcanoHexAtLocations(hexes.keySet()) ) {
            throw new NukeVolcanoHexRuleException();
        }
    }

    private boolean isThereAVolcanoHexAtLocations(Set<Location> locations) {

        for( Location location : locations) {

            Hex hex = board.getHex(location);
            Terrain hexTerrain = hex.getTerrain();

            if ( hexTerrain instanceof Volcano ) {
               return true;
            }
        }

        return false;
    }
}
