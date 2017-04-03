package tigerislandserver.command;

import tigerisland.board.Location;
import tigerisland.tile.Tile;

public class PlaceTileCommand
{
    public final Location placementLocation;
    public final Tile tile;

    public PlaceTileCommand(Tile tile, Location loc)
    {
        this.tile=tile;
        this.placementLocation=loc;
    }
}
