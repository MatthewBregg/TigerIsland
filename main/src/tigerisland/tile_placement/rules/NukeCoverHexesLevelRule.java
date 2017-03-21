package tigerisland.tile_placement.rules;


import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;
import tigerisland.tile_placement.exceptions.NukeCoverHexesLevelRuleException;
import tigerisland.tile_placement.TilePlacement;

import java.util.Map;

public class NukeCoverHexesLevelRule implements TilePlacement {

    Board board;

    public NukeCoverHexesLevelRule(Board board) {
        this.board = board;
    }

    @Override
    public void placeTile(Tile tile, Location referenceLocation) throws Throwable {

        Map<Location, Hex> hexes = TileUnpacker.getTileHexes(tile, referenceLocation);

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
