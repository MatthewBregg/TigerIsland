package tigerisland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by josh on 3/16/17.
 */

public class TilePlacer {

    private static  List<TilePlacer> tilePlacers = new ArrayList();
    private static Iterator placersIter;
    private static TilePlacer currentPlacer;

    protected static int num;
    private static Tile tile;
    protected static  Location volcanoLoc;
    private static HexBoard board;


    private static TileHexLocationFactory nonVolcanoHexFinder = new TileHexLocationFactoryImp();

    private static HashMap<TileHexLocationFactory.NonVolcanoTile, Location> otherHexLocations = new HashMap<>();

    protected static  List<Location> usedLocations;

    protected static Location left;
    protected static Location right;

    private void initialize(){
        tilePlacers.add(new AdjacentToBoardTilePlacer());
        tilePlacers.add(new OnBoardTilePlacer());
        //tilePlacers.add()
        placersIter = tilePlacers.iterator();
        currentPlacer = tilePlacers.get(0);
    }

    public void placeTile(Tile tile, Location loc, HexBoard board){

        this.tile = tile;
        this.volcanoLoc = loc;
        this.board = board;

        usedLocations = this.board.getUsedBoardLocations();

        otherHexLocations = nonVolcanoHexFinder.getNonVolcanoHexLocations(volcanoLoc, tile.getOrientation());
        left = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.LEFT);
        right = otherHexLocations.get(TileHexLocationFactory.NonVolcanoTile.RIGHT);
        this.initialize();
        currentPlacer.tryPlaceTile();
    }

    protected void tryPlaceTile(){}

    protected void tryNextPlacer(){
        if (!placersIter.hasNext())
            System.exit(0);
//            throw new Exception("No more placers; your location or orientation are incorrect");
        currentPlacer = (TilePlacer) placersIter.next();
        currentPlacer.tryPlaceTile();
    }
}




