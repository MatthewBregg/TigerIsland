package tigerislandserver.adapter;

import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.hex.Hex;
import tigerisland.player.Player;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerislandserver.command.PlaceTileCommand;
import tigerislandserver.gameplay.GameThread;
import tigerislandserver.server.TournamentPlayer;
import tigerislandserver.tournament.Tournament;

public class GameInputAdapter
{
    private static boolean isValidTilePlacementCommand(String[] inputTokens)
    {
        return inputTokens[6].equals("PLACE")
                && isValidTile(inputTokens[7])
                && inputTokens[8].equals("AT")
                && isValidNumber(inputTokens[9])
                && isValidNumber(inputTokens[10])
                && isValidNumber(inputTokens[11])
                && isValidOrientation(inputTokens[12]);
    }

    private static boolean placeTile(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[8]);
        int y = Integer.parseInt(inputTokens[9]);
        int z = Integer.parseInt(inputTokens[10]);

        if(x+y+z != 0)
        {
            return false;
        }

        String[] terrains = inputTokens[7].split("\\+");

        Terrain bottomRight = getTerrain(terrains[0]);
        Terrain bottomLeft = getTerrain(terrains[1]);

        Location loc=new Location(x, y, z);
        Tile tile = new Tile(0, new Hex(bottomLeft), new Hex(bottomRight));

        rotateTile(tile, inputTokens[12]);

        GameManager gm = game.getGameManager();

        return gm.placeTile(tile, loc);
    }

    private static boolean isFoundSettlementCommand(String[] inputTokens)
    {
        return inputTokens[13].equals("FOUND")
                && inputTokens[14].equals("SETTLEMENT")
                && inputTokens[15].equals("AT")
                && isValidNumber(inputTokens[16])
                && isValidNumber(inputTokens[17])
                && isValidNumber(inputTokens[18]);
    }

    private static void foundSettlement(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[16]);
        int y = Integer.parseInt(inputTokens[17]);
        int z = Integer.parseInt(inputTokens[18]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        gm.foundSettlement(loc, p);
    }

    private static boolean isExpandSettlementCommand(String[] inputTokens)
    {
        return inputTokens[13].equals("EXPAND")
                && inputTokens[14].equals("SETTLEMENT")
                && inputTokens[15].equals("AT")
                && isValidNumber(inputTokens[16])
                && isValidNumber(inputTokens[17])
                && isValidNumber(inputTokens[18])
                && isValidTerrain(inputTokens[19]);
    }

    private static void expandSettlement(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[16]);
        int y = Integer.parseInt(inputTokens[17]);
        int z = Integer.parseInt(inputTokens[18]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);
        Terrain t=getTerrain(inputTokens[19]);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        gm.expandSettlement(loc, t, p);
    }

    private static boolean isBuildTotoroCommand(String[] inputTokens)
    {
        return inputTokens[13].equals("BUILD")
                && inputTokens[14].equals("TOTORO")
                && inputTokens[15].equals("SANCTUARY")
                && inputTokens[16].equals("AT")
                && isValidNumber(inputTokens[17])
                && isValidNumber(inputTokens[18])
                && isValidNumber(inputTokens[19]);
    }

    private static void buildTotoro(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[17]);
        int y = Integer.parseInt(inputTokens[18]);
        int z = Integer.parseInt(inputTokens[19]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        gm.buildTotoro(loc, p);
    }

    private static boolean isBuildTigerCommand(String[] inputTokens)
    {
        return inputTokens[13].equals("BUILD")
                && inputTokens[14].equals("TIGER")
                && inputTokens[15].equals("PLAYGROUND")
                && inputTokens[16].equals("AT")
                && isValidNumber(inputTokens[17])
                && isValidNumber(inputTokens[18])
                && isValidNumber(inputTokens[19]);
    }

    private static void buildTiger(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[17]);
        int y = Integer.parseInt(inputTokens[18]);
        int z = Integer.parseInt(inputTokens[19]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        gm.buildTiger(loc, p);
    }

    private static boolean isUnableToBuild(String[] inputTokens)
    {
        return inputTokens[13].equals("UNABLE")
                && inputTokens[14].equals("TO")
                && inputTokens[15].equals("BUILD");
    }

    private static void failToBuild(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        GameManager gm = game.getGameManager();
        game.unableToBuild(tournamentPlayer);
    }

    private static PlaceTileCommand calculatePlaceTileCommand(String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[8]);
        int y = Integer.parseInt(inputTokens[9]);
        int z = Integer.parseInt(inputTokens[10]);

        if(x+y+z != 0)
        {
            return null;
        }

        String[] terrains = inputTokens[7].split("\\+");

        Terrain bottomRight = getTerrain(terrains[0]);
        Terrain bottomLeft = getTerrain(terrains[1]);

        Location loc=new Location(x, y, z);
        Tile tile = new Tile(0, new Hex(bottomLeft), new Hex(bottomRight));

        rotateTile(tile, inputTokens[12]);

        return new PlaceTileCommand(tile, loc);

    }

    private static void rotateTile(Tile tile, String orientation)
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

    private static Terrain getTerrain(String terrain)
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

    private static boolean isValidNumber(String inputToken)
    {
        return inputToken.matches("^(\\+|-)?\\d+$");
    }

    private static boolean isValidGameId(String s)
    {
        return s.equals("A") || s.equals("B");
    }


    private static boolean isValidGameMoveCommand(String[] inputTokens)
    {
        return inputTokens.length > 12
                && inputTokens[0].equals("GAME")
                && isValidGameId(inputTokens[1])
                && inputTokens[2].equals("MOVE")
                && isValidNumber(inputTokens[3]);
    }

    public static void makeMove(GameThread game, TournamentPlayer tournamentPlayer, String input, char gid, int moveNumber, Tile tile)
    {
        String[] inputTokens = input.split("\\s+");

        if(isValidGameMoveCommand(inputTokens)
                && isCorrectGame(inputTokens, gid)
                && isCorrectMoveNumber(inputTokens, moveNumber))
        {
            if(isValidTilePlacementCommand(inputTokens)
                    && isCorrectTile(inputTokens, tile))
            {
                if(!placeTile(game, tournamentPlayer, inputTokens))
                {
                    game.invalidTilePlacement(tournamentPlayer);
                    return;
                }

                if(isFoundSettlementCommand(inputTokens))
                {
                    foundSettlement(game, tournamentPlayer, inputTokens);
                }
                else if(isExpandSettlementCommand(inputTokens))
                {
                    expandSettlement(game, tournamentPlayer, inputTokens);
                }
                else if(isBuildTotoroCommand(inputTokens))
                {
                    buildTotoro(game, tournamentPlayer, inputTokens);
                }
                else if(isBuildTigerCommand(inputTokens))
                {
                    buildTiger(game, tournamentPlayer, inputTokens);
                }
                else if(isUnableToBuild(inputTokens))
                {
                    failToBuild(game, tournamentPlayer, inputTokens);
                }
            }
            else
            {
                //TODO Log Invalid Message Provided
                game.timeout(tournamentPlayer);
                return;
            }
        }
    }

    private static boolean isCorrectTile(String[] inputTokens, Tile tile)
    {
        return inputTokens[7].equals(tile.getStringOfTerrains());
    }

    private static boolean isCorrectMoveNumber(String[] inputTokens, int moveNumber)
    {
        return Integer.parseInt(inputTokens[3]) == moveNumber;
    }

    private static boolean isCorrectGame(String[] inputTokens, char gid)
    {
        return inputTokens[1].charAt(0) == gid;
    }
}
