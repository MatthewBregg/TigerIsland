package tigerisland.tile_placement;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NukeTilePlacer  implements  TilePlacement, TilePlacementChain{

    TilePlacement nextTilePlacement;
    List<TilePlacement> nukeTilePlacementRules;
    Board board;

    public NukeTilePlacer(Board board, TilePlacement... rules) {

        this.board = board;
        nukeTilePlacementRules = new ArrayList<>();
        for(TilePlacement rule : rules) {
            nukeTilePlacementRules.add(rule);
        }
    }
    
    @Override
    public void placeTile(Tile tile, Location location) throws Throwable {

        Map<Location, Hex> hexes = TileUnpacker.getTileHexes(tile, location);

        if ( aHexIsUnUsed(hexes) ) {
            nextTilePlacement(tile, location);
        }
        else {

            applyNukeRules(tile, location);

            placeHexesOnBoard(hexes);
        }
    }

    private void applyNukeRules(Tile tile, Location location) throws Throwable {
        for(TilePlacement rule : nukeTilePlacementRules) {
                rule.placeTile(tile, location);
        }
    }

    private boolean aHexIsUnUsed(Map<Location, Hex> hexes) {

        for(Location location : hexes.keySet()) {

            if (this.board.isLocationUsed(location) == false) {
                return true;
            }
        }
        return false;
    }

    private void placeHexesOnBoard(Map<Location, Hex> hexes) {
        hexes.forEach( (location, hex) -> {

           Hex mapHex = this.board.getHex(location);
           hex.setLevel(mapHex.getLevel()+1);
           this.board.placeHex(location, hex);

        });
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
        this.nextTilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) throws Throwable {
        this.nextTilePlacement.placeTile(tile, location);
    }
}
