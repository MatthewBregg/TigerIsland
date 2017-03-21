package tigerisland.tile_placement.rules;


import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile_placement.exceptions.NukeHexesOfDifferentTilesRuleException;

import java.util.HashMap;
import java.util.Map;

public class NukeHexesMustBeOfDifferentTilesRule implements NukePlacementRule {

    Board board;

    public NukeHexesMustBeOfDifferentTilesRule(Board board) {
        this.board = board;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws Throwable {

        if ( !hexesBelongToTwoOrMoreTiles(hexes) ) {
           throw new NukeHexesOfDifferentTilesRuleException();
        }
    }

    private boolean hexesBelongToTwoOrMoreTiles(Map<Location, Hex> hexes) {
        Map<Number,Number> hexesIds = new HashMap<>();

        hexes.forEach( (location, hex) -> {
            hexesIds.put(hex.getTileID(), 1);
        });

        return hexesIds.size() >= 2;
    }
}
