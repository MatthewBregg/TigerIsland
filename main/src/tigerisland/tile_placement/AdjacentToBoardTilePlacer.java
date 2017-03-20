package tigerisland.tile_placement;

import tigerisland.board.Board;
import tigerisland.hex.Hex;
import tigerisland.board.Location;
import tigerisland.hex.NonVolcanoHex;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;
import tigerisland.tile.Tile;
import tigerisland.tile.TileHexFinder;

import java.util.HashMap;
import java.util.List;

public class AdjacentToBoardTilePlacer implements TilePlacement, TilePlacementChain{

    Board board;
    TilePlacement tilePlacement;
    TileHexFinder tileHexLocationFactory;

    public AdjacentToBoardTilePlacer(Board board, TileHexFinder tileHexLocationFactory) {
        this.board = board;
        this.tileHexLocationFactory = tileHexLocationFactory;
    }

    @Override
    public void placeTile(Tile tile, Location volcanoHexLocation) throws Throwable {

        HashMap<NonVolcanoHex, Location> locations =
                tileHexLocationFactory.getNonVolcanoHexLocations(volcanoHexLocation, tile.getOrientation());

        Location leftLocation = locations.get(NonVolcanoHex.LEFT);
        Location rightLocation = locations.get(NonVolcanoHex.RIGHT);

        List<Location> usedLocations = board.getUsedBoardLocations();
        for( Location usedLocation : usedLocations) {

            if (usedLocation.equals(volcanoHexLocation)) {
                nextTilePlacement(tile, volcanoHexLocation);
                return;
            }
            else if(usedLocation.equals(leftLocation)) {
                nextTilePlacement(tile, volcanoHexLocation);
                return;
            }
            else if(usedLocation.equals(rightLocation)){
               nextTilePlacement(tile, volcanoHexLocation);
               return;
            }
        }

        Hex volcanoHex = tile.getReferenceHex();

        Hex leftHex = tile.getLeftHex();

        Hex rightHex = tile.getRightHex();

        board.placeHex(volcanoHexLocation, volcanoHex);
        board.placeHex(rightLocation, rightHex);
        board.placeHex(leftLocation, leftHex);

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
