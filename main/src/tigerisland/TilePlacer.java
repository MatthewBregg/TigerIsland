package tigerisland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josh on 3/16/17.
 */

public class TilePlacer {

    private List<Class<?>> tilePlacers = new ArrayList();
    private Iterator placersIter;
    private TilePlacer currentPlacer;

    protected Tile tile;
    protected Location volcanoLoc;
    protected HexBoard board;


    protected TileHexLocationFactory nonVolcanoHexFinder = new TileHexLocationFactoryImp();
    protected HashMap<TileHexLocationFactory.NonVolcanoTile, Location> otherHexLocations = new HashMap<>();

    protected List<Location> usedLocations;

    protected Location left;
    protected Location right;


    public TilePlacer(){
        tilePlacers.add(AdjacentToBoardTilePlacer.class);
       tilePlacers.add(OnBoardTilePlacer.class);
        //tilePlacers.add()
        placersIter = tilePlacers.iterator();
    }

    public void placeTile(Tile tile, Location loc, HexBoard board){
        this.tile = tile;
        this.volcanoLoc = loc;
        this.board = board;

        usedLocations = this.board.getUsedBoardLocations();

        otherHexLocations = nonVolcanoHexFinder.getNonVolcanoHexLocations(volcanoLoc, tile.getOrientation());
        left = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.LEFT);
        right = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.RIGHT);
        this.tryPlaceTile();
    }

    protected void tryPlaceTile (){
        currentPlacer.tryPlaceTile();
    }

    protected void tryNextPlacer(){
//        try{
//
//        }
//        if (!placersIter.hasNext())
//            throw new Exception("No more placers; your location or orientation are incorrect");
        currentPlacer = (TilePlacer) placersIter.next();
        this.tryPlaceTile();
    }
}




