package tigerisland.tile_placement.rules;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile_placement.exceptions.NukeVolcanoOnVolcanoRuleException;

import java.util.Map;

public class NukeVolcanoOnVolcanoRule implements NukePlacementRule{

    Board board;

    public NukeVolcanoOnVolcanoRule(Board board) {
       this.board = board;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws Exception {

            if ( !isTileVolcanoOnBoardVolcano(hexes) ) {
               throw new NukeVolcanoOnVolcanoRuleException();
            }
    }

    private boolean isTileVolcanoOnBoardVolcano(Map<Location, Hex> hexes) {

        for( Location location : hexes.keySet()) {

            Hex hex = hexes.get(location);
            Terrain hexTerrain = hex.getTerrain();

            if ( hexTerrain instanceof Volcano) {

                 Hex boardHex = board.getHex(location);
                 Terrain boardHexTerrain = boardHex.getTerrain();

                 return boardHexTerrain instanceof Volcano;
            }
        }

        return false;
    }
}
