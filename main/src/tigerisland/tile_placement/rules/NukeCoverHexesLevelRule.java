package tigerisland.tile_placement.rules;


import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile_placement.exceptions.NukeCoverHexesLevelRuleException;

import java.util.Map;

public class NukeCoverHexesLevelRule implements NukePlacementRule{

    Board board;

    public NukeCoverHexesLevelRule(Board board) {
        this.board = board;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws Throwable {
        int expectedLevel = -1;
        for (Location location : hexes.keySet() ) {

            Hex hex = board.getHex(location);
            int hexLevel = hex.getLevel();

            if (expectedLevel == -1) {
               expectedLevel = hexLevel;
            }
            else if (hexLevel != expectedLevel) {
               throw new NukeCoverHexesLevelRuleException();
            }
        }
    }
}
