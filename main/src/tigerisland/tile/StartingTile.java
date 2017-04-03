package tigerisland.tile;

import tigerisland.hex.Hex;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;

/**
 * Created by Philip on 4/2/2017.
 */
public class StartingTile extends Tile{
    private Hex topLeft, topRight;

    public StartingTile(){
        super(99, new Hex(Rocky.getInstance()), new Hex(Grassland.getInstance()));

        topLeft = new Hex(Jungle.getInstance());
        topRight = new Hex(Lake.getInstance());
    }

    public void setOrientation(Orientation orientation){
        return;
    }
}
