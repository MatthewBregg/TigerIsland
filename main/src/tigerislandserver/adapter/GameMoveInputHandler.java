package tigerislandserver.adapter;

import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerislandserver.command.PlaceTileCommand;

public class GameMoveInputHandler extends InputAdapter
{
    private PlaceTileCommand placeTileCommand;

    public GameMoveInputHandler(String input)
    {
        super(input);
    }

    @Override
    public void run()
    {
        if(isValidTilePlacementCommand())
        {
            this.placeTileCommand = calculatePlaceTileCommand();

            if(isFoundSettlementCommand())
            {
                //TODO
            }
            else if(isExpandSettlementCommand())
            {
                //TODO
            }
            else if(isBuildTotoroCommand())
            {
                //TODO
            }
            else if(isBuildTigerCommand())
            {
                //TODO
            }
            else if(isUnableToBuild())
            {
                //TODO
            }
        }
        else
        {
            return;
        }
    }

    private boolean isFoundSettlementCommand()
    {
        //TODO
        return true;
    }

    private boolean isExpandSettlementCommand()
    {
        //TODO
        return true;
    }

    private boolean isBuildTotoroCommand()
    {
        //TODO
        return true;
    }

    private boolean isBuildTigerCommand()
    {
        //TODO
        return true;
    }

    private boolean isUnableToBuild()
    {
        return getInputTokens()[0].equals("UNABLE")
                && getInputTokens()[0].equals("TO")
                && getInputTokens()[0].equals("BUILD");
    }

    private PlaceTileCommand calculatePlaceTileCommand()
    {
        int x = Integer.parseInt(getInputTokens()[8]);
        int y = Integer.parseInt(getInputTokens()[9]);
        int z = Integer.parseInt(getInputTokens()[10]);

        if(x+y+z != 0)
        {
            return null;
        }

        String[] terrains = getInputTokens()[7].split("\\+");

        Terrain bottomRight = getTerrain(terrains[0]);
        Terrain bottomLeft = getTerrain(terrains[1]);

        Location loc=new Location(x, y, z);
        Tile tile = new Tile(0, new Hex(bottomLeft), new Hex(bottomRight));

        rotateTile(tile, getInputTokens()[12]);

        return new PlaceTileCommand(tile, loc);

    }

    private void rotateTile(Tile tile, String orientation)
    {
        switch(orientation)
        {
            case "1":
                tile.setOrientation(Orientation.getWest());
                break;
            case "2":
                tile.setOrientation(Orientation.getNorthWest());
                break;
            case "3":
                tile.setOrientation(Orientation.getNorthEast());
                break;
            case "4":
                tile.setOrientation(Orientation.getEast());
                break;
            case "5":
                tile.setOrientation(Orientation.getSouthEast());
                break;
            case "6":
                tile.setOrientation(Orientation.getSouthWest());
                break;
        }
    }

    private Terrain getTerrain(String terrain)
    {
        switch (terrain)
        {
            case "JUNGLE":
                return Jungle.getInstance();
            case "LAKE":
                return Lake.getInstance();
            case "GRASS":
                return Grassland.getInstance();
            case "ROCK":
                return Rocky.getInstance();
            default:
                return null;
        }
    }

    private boolean isValidTilePlacementCommand()
    {
        return getInputTokens()[6].equals("PLACE")
                && isValidTile(getInputTokens()[7])
                && getInputTokens()[8].equals("AT")
                && isValidNumber(getInputTokens()[9])
                && isValidNumber(getInputTokens()[10])
                && isValidNumber(getInputTokens()[11])
                && isValidOrientation(getInputTokens()[12]);
    }

    private static boolean isValidOrientation(String inputToken)
    {
        return inputToken.matches("^[1-6]$");
    }

    private static boolean isValidTile(String inputToken)
    {
        String[] terrains = inputToken.split("\\+");

        return terrains.length == 2
                && isValidTerrain(terrains[0])
                && isValidTerrain(terrains[1]);
    }

    private static boolean isValidTerrain(String terrain)
    {
        return terrain.equals("JUNGLE")
                || terrain.equals("LAKE")
                || terrain.equals("GRASS")
                || terrain.equals("ROCK");
    }
}
