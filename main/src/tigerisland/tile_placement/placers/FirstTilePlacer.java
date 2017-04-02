package tigerisland.tile_placement.placers;

import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.tile.TileUnpacker;

import java.util.Map;

public class FirstTilePlacer implements TilePlacement, TilePlacementChain {

    private Board board;
    private TilePlacement nextTilePlacement;
    private Location zeroLocation;

    public FirstTilePlacer(Board board) {
        this.board = board;
        zeroLocation = new Location(0, 0, 0);
    }

    @Override
    public void placeTile(Tile tile, Location referenceLocation) throws Exception {

        if (isBoardEmpty() && isLocationZero(referenceLocation)) {
            Map<Location, Hex> hexes = TileUnpacker.getTileHexes(tile, referenceLocation);
            placeHexesOnBoard(hexes);
        } else {
            nextTilePlacement(tile, referenceLocation);
        }
    }

    private void placeHexesOnBoard(Map<Location, Hex> hexes) {

        hexes.forEach((location, hex) -> {
            hex.setLevel(1);
            board.placeHex(location, hex);
        });
    }

    @Override
    public void setNextTilePlacement(TilePlacement tilePlacement) {
        this.nextTilePlacement = tilePlacement;
    }

    @Override
    public void nextTilePlacement(Tile tile, Location location) throws Exception {
        nextTilePlacement.placeTile(tile, location);
    }

    public boolean isBoardEmpty() {
        return board.getSize() <= 0;
    }

    public boolean isLocationZero(Location location) {
        return zeroLocation.equals(location);
    }

}
