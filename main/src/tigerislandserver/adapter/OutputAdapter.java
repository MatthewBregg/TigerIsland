package tigerislandserver.adapter;

import tigerisland.tile.Tile;
import tigerislandserver.command.*;

public class OutputAdapter extends Thread
{
    public OutputAdapter(){}

    public void output(PlaceTileCommand placeCommand, BuildTigerCommand buildCommand)
    {

    }

    public static String getMoveRequestMessage(char gid, int moveNumber, Tile tile)
    {
        return "MAKE YOUR MOVE IN GAME " + gid + " WITHIN 1.5 SECONDS: MOVE " + moveNumber + " PLACE " + tile.getStringOfTerrains();
    }
}
