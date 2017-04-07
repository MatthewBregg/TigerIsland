package tigerisland.tile_placement.rules;


import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile_placement.exceptions.NukeHexesOfDifferentTilesRuleException;
import tigerisland.tile_placement.exceptions.TilePlacementException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NukeHexesOfDifferentTilesRule implements NukePlacementRule {

    private Board board;

    public NukeHexesOfDifferentTilesRule(Board board) {
        this.board = board;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws TilePlacementException {

        if ( !hexesBelongToTwoOrMoreTiles(hexes) ) {
           throw new NukeHexesOfDifferentTilesRuleException();
        }
    }

    private boolean hexesBelongToTwoOrMoreTiles(Map<Location, Hex> hexes) {
        Set<Integer> tileIds = new HashSet<>();
        for ( Location loc : hexes.keySet() ) {
            tileIds.add(board.getHex(loc).getTileID());
        }
        return tileIds.size() > 1;
    }
}
