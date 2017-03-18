package tigerisland;


import java.util.HashMap;

public class AdjacentToBoardTilePlacer extends TilePlacer {
    //case A none of the tile hexes cover an existing map hex

    //location is the volcano tile

    public void tryPlaceTile() {

        if (board.getUsedBoardLocations().contains(volcanoLoc))
            this.tryNextPlacer();

        TileHexLocationFactory findOtherHexes = new TileHexLocationFactoryImp();


        HashMap<TileHexLocationFactory.NonVolcanoTile, Location> otherHexLocations = new HashMap<>();
        otherHexLocations = findOtherHexes.getNonVolcanoHexLocations(volcanoLoc, tile.getOrientation());

        Location left = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.LEFT);
        Location right = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.RIGHT);

        for (Location location : board.getUsedBoardLocations()){
            if (location == left || location == right)
                this.tryNextPlacer();
        }
        board.placeHex(volcanoLoc, new Hex ());
        board.placeHex(left, new Hex());
        board.placeHex(right, new Hex());
    }

}
