package tigerisland.tile_placement;

import tigerisland.*;

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

        Hex volcanoHex = new Hex(tile.getID(), 1, Volcano.getInstance(), 1);

        Terrain leftTerrain = tile.getLeftTerrain();
        Hex leftHex = new Hex(tile.getID(), 1, leftTerrain, 1);

        Terrain rightTerrain = tile.getRightTerrain();
        Hex rightHex = new Hex(tile.getID(), 1, rightTerrain, 1);

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
