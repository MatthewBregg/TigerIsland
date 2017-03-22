package tigerisland.tile_placement;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;

import java.util.List;
import java.util.Map;


public class AdjacentToBoardTilePlacer implements TilePlacement, TilePlacementChain{

    Board board;
    TilePlacement tilePlacement;

    public AdjacentToBoardTilePlacer(Board board) {
        this.board = board;
    }

    @Override
    public void placeTile(Tile tile, Location referenceLocation) throws Throwable {

        Map<Location, Hex> hexes = TileUnpacker.getTileHexes(tile, referenceLocation);

        if ( aLocationIsAlreadyUsed(hexes) || !checkForAdjacentHexes(hexes)) {
            nextTilePlacement(tile, referenceLocation);
        }
        else {
            placeHexesOnBoard(hexes);
        }
    }

    private boolean checkForAdjacentHexes(Map<Location, Hex> hexes) {
        for ( Location location : hexes.keySet()) {
            if ( aHexIsAdjacentToThisLocation(location)) {
                return true;
            }
        }
        return false;
    }

    private boolean aHexIsAdjacentToThisLocation(Location location) {
        List<Location> surroundingLocations = location.getSurroundingLocations();
        for ( Location adjLoc : surroundingLocations ) {
            if ( this.board.isLocationUsed(adjLoc)) {
                return true;
            }
        }
        return false;
    }

    private boolean aLocationIsAlreadyUsed(Map<Location, Hex> hexes) {
        for(Location location : hexes.keySet()) {

            if (this.board.isLocationUsed(location)) {
               return true;
            }
        }
        return false;
    }

    private void placeHexesOnBoard(Map<Location, Hex> hexes) {
        hexes.forEach((location, hex) -> {
            this.board.placeHex(location, hex);
        });
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
       this.tilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) throws Throwable {
        if (tilePlacement != null)
            tilePlacement.placeTile(tile, location);
    }
}
