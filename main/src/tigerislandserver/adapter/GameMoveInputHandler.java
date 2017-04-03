package tigerislandserver.adapter;

import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerislandserver.command.PlaceTileCommand;
import tigerislandserver.tournament.Tournament;

public class GameMoveInputHandler extends InputAdapter
{
    private PlaceTileCommand placeTileCommand;
    private Tournament tournament;

    public GameMoveInputHandler(String input)
    {
        super(input);
        tournament = Tournament.getInstance();
    }

    @Override
    public void run()
    {
        if(isValidTilePlacementCommand())
        {
            this.placeTileCommand = calculatePlaceTileCommand();

            if(isFoundSettlementCommand())
            {
                foundSettlement();
            }
            else if(isExpandSettlementCommand())
            {
                expandSettlement();
            }
            else if(isBuildTotoroCommand())
            {
                buildTotoro();
            }
            else if(isBuildTigerCommand())
            {
                buildTiger();
            }
            else if(isUnableToBuild())
            {
                failToBuild();
            }
        }
        else
        {
            return;
        }
    }

    private boolean isFoundSettlementCommand()
    {
        return getInputTokens()[13].equals("FOUND")
                && getInputTokens()[14].equals("SETTLEMENT")
                && getInputTokens()[15].equals("AT")
                && isValidNumber(getInputTokens()[16])
                && isValidNumber(getInputTokens()[17])
                && isValidNumber(getInputTokens()[18]);
    }

    private void foundSettlement()
    {

    }

    private boolean isExpandSettlementCommand()
    {
        return getInputTokens()[13].equals("EXPAND")
                && getInputTokens()[14].equals("SETTLEMENT")
                && getInputTokens()[15].equals("AT")
                && isValidNumber(getInputTokens()[16])
                && isValidNumber(getInputTokens()[17])
                && isValidNumber(getInputTokens()[18])
                && isValidTerrain(getInputTokens()[19]);
    }

    private void expandSettlement()
    {

    }

    private boolean isBuildTotoroCommand()
    {
        return getInputTokens()[13].equals("BUILD")
                && getInputTokens()[14].equals("TOTORO")
                && getInputTokens()[15].equals("SANCTUARY")
                && getInputTokens()[16].equals("AT")
                && isValidNumber(getInputTokens()[17])
                && isValidNumber(getInputTokens()[18])
                && isValidNumber(getInputTokens()[19]);
    }

    private void buildTotoro()
    {

    }

    private boolean isBuildTigerCommand()
    {
        return getInputTokens()[13].equals("BUILD")
                && getInputTokens()[14].equals("TIGER")
                && getInputTokens()[15].equals("PLAYGROUND")
                && getInputTokens()[16].equals("AT")
                && isValidNumber(getInputTokens()[17])
                && isValidNumber(getInputTokens()[18])
                && isValidNumber(getInputTokens()[19]);
    }

    private void buildTiger()
    {

    }

    private boolean isUnableToBuild()
    {
        return getInputTokens()[13].equals("UNABLE")
                && getInputTokens()[14].equals("TO")
                && getInputTokens()[15].equals("BUILD");
    }

    private void failToBuild()
    {

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
                return null; //Impossible to reach
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
