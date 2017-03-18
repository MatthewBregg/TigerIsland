package tigerisland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileHexLocationFactoryImp implements TileHexLocationFactory {


//    public final static int EAST=0;
//    public final static int NORTHEAST=60;
//    public final static int NORTHWEST=120;
//    public final static int WEST=180;
//    public final static int SOUTHWEST=240;
//    public final static int SOUTHEAST=300;


    public HashMap<NonVolcanoTile, Location> getNonVolcanoHexLocations(Location volcanoHexLocation, Orientation tileOrientation){

        HashMap<NonVolcanoTile, Location> nonVolcanoHexes = new HashMap<>();

        switch(tileOrientation.getAngle()){
            case Orientation.EAST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getSouthWest()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthEast()));
                return nonVolcanoHexes;
            case Orientation.NORTHEAST:
                nonVolcanoHexes.put(NonVolcanoTile.LEFT, volcanoHexLocation.getAdjacent(Orientation.getEast()));
                nonVolcanoHexes.put(NonVolcanoTile.RIGHT, volcanoHexLocation.getAdjacent(Orientation.getSouthEast()));
                return nonVolcanoHexes;
            default:
                return nonVolcanoHexes;

        }





    }



}
