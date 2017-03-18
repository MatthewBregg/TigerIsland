package tigerisland;


import java.util.HashMap;

public class AdjacentToBoardTilePlacer implements TilePlacer {
    //case A none of the tile hexes cover an existing map hex

    //location is the volcano tile

    public void placeTile(Tile tile, Location volcanoLoc, HexBoard board) {

        TileHexLocationFactory findOtherHexes = new TileHexLocationFactoryImp();


        HashMap<TileHexLocationFactory.NonVolcanoTile, Location> otherHexLocations = findOtherHexes.getNonVolcanoHexLocations(volcanoLoc, tile.getOrientation());

//        for (location : board.getUsedBoardLocations())
//
//
//            if (hex.getUsedLocations().contains)

    }

}
