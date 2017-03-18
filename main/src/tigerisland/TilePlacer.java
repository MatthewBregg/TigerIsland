package tigerisland;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josh on 3/16/17.
 */


public class TilePlacer {

    private List<Class<?>> tilePlacers;
    private Iterator placersIter;
    private TilePlacer currentPlacer;

    protected Tile tile;
    protected Location volcanoLoc;
    protected HexBoard board;


    public TilePlacer(){
       tilePlacers = new ArrayList();
       tilePlacers.add(AdjacentToBoardTilePlacer.class);
       tilePlacers.add(OnBoardTilePlacer.class);
        //tilePlacers.add()
        placersIter = tilePlacers.iterator();
    }


    public void placeTile(Tile tile, Location loc, HexBoard board){
        this.tile = tile;
        this.volcanoLoc = loc;
        this.board = board;
        this.tryPlaceTile();
    }

    protected void tryPlaceTile (){
        currentPlacer.tryPlaceTile();
    }

    protected void tryNextPlacer(){
//        try{
//
//        }
//
//
//
//
//        if (!placersIter.hasNext())
//            throw new Exception("No more placers; your location or orientation are incorrect");
        currentPlacer = (TilePlacer) placersIter.next();
        this.tryPlaceTile();
    }
}




//public OnTopOfTiles implements tigerisland.TilePlacer{
//
//    }
//
//
//
//
//    //case B all of the tiles cover existing hexes
//
//
//    //all covered hexes must be part of 2 or 3 diff tiles
//
//    //none of hexes contain size of 1 stee
//
//}
//
//
//public InvalidPlacementHandler implements tigerisland.TilePlacer{
//
//
//        }