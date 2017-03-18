package tigerisland;


public class AdjacentToBoardTilePlacer extends TilePlacer {
    public AdjacentToBoardTilePlacer() {

    }
    //case A none of the tile hexes cover an existing map hex

    //location is the volcano tile


    public void tryPlaceTile() {
        ++num;

        super.tryNextPlacer();
////        for (Location location : board.getUsedBoardLocations()){
////            if (location == left || location == right || location == volcanoLoc)
////                super.tryNextPlacer();
////        }
//        board.placeHex(volcanoLoc, new Hex ());
//        board.placeHex(left, new Hex());
//        board.placeHex(right, new Hex());
    }


}
