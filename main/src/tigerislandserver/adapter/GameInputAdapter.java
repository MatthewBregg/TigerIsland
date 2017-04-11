package tigerislandserver.adapter;

import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerislandserver.gameplay.GameThread;
import tigerislandserver.server.TournamentPlayer;

public class GameInputAdapter
{
    public static void makeMove(GameThread game, TournamentPlayer tournamentPlayer, String input,
                                char gid, int moveNumber, Tile tile)
    {
        System.out.println("CLIENT "+ tournamentPlayer.getID().getId()+": " + input);

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
                    OutputAdapter.sendIllegalTilePlacementMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
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
                else
                {
                    OutputAdapter.sendIllegalFormatedMessageMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
                }
            }
            else
            {
                OutputAdapter.sendIllegalFormatedMessageMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
            }
        }
        else
        {
            OutputAdapter.sendIllegalFormatedMessageMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        }
    }



    private static boolean isValidTilePlacementCommand(String[] inputTokens)
    {
        return inputTokens[4].equals("PLACE")
                && isValidTile(inputTokens[5])
                && inputTokens[6].equals("AT")
                && isValidNumber(inputTokens[7])
                && isValidNumber(inputTokens[8])
                && isValidNumber(inputTokens[9])
                && isValidOrientation(inputTokens[10]);
    }

    private static boolean placeTile(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[7]);
        int y = Integer.parseInt(inputTokens[8]);
        int z = Integer.parseInt(inputTokens[9]);

        if(x+y+z != 0)
        {
            return false;
        }

        String[] terrains = inputTokens[5].split("\\+");

        Terrain bottomRight = getTerrain(terrains[0]);
        Terrain bottomLeft = getTerrain(terrains[1]);

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();

        Tile tile = new Tile(gm.getTileId(), bottomLeft, bottomRight);

        rotateTile(tile, inputTokens[10]);

        return gm.placeTile(tile, loc);
    }

    private static boolean isFoundSettlementCommand(String[] inputTokens)
    {
        return inputTokens[11].equals("FOUND")
                && inputTokens[12].equals("SETTLEMENT")
                && inputTokens[13].equals("AT")
                && isValidNumber(inputTokens[14])
                && isValidNumber(inputTokens[15])
                && isValidNumber(inputTokens[16]);
    }

    private static void foundSettlement(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[14]);
        int y = Integer.parseInt(inputTokens[15]);
        int z = Integer.parseInt(inputTokens[16]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());

        if(gm.foundSettlement(loc, p))
        {
            OutputAdapter.sendFoundedSettlementMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        }
        else
        {
            OutputAdapter.sendIllegalBuildMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
            game.invalidBuild(tournamentPlayer);
        }
    }

    private static boolean isExpandSettlementCommand(String[] inputTokens)
    {
        return inputTokens[11].equals("EXPAND")
                && inputTokens[12].equals("SETTLEMENT")
                && inputTokens[13].equals("AT")
                && isValidNumber(inputTokens[14])
                && isValidNumber(inputTokens[15])
                && isValidNumber(inputTokens[16])
                && isValidTerrain(inputTokens[17]);
    }

    private static void expandSettlement(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[14]);
        int y = Integer.parseInt(inputTokens[15]);
        int z = Integer.parseInt(inputTokens[16]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);
        Terrain t=getTerrain(inputTokens[17]);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        if(gm.expandSettlement(loc, t, p))
        {
            OutputAdapter.sendExpandedSettlementMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        }
        else
        {
            OutputAdapter.sendIllegalBuildMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
            game.invalidBuild(tournamentPlayer);
        }
    }

    private static boolean isBuildTotoroCommand(String[] inputTokens)
    {
        return inputTokens[11].equals("BUILD")
                && inputTokens[12].equals("TOTORO")
                && inputTokens[13].equals("SANCTUARY")
                && inputTokens[14].equals("AT")
                && isValidNumber(inputTokens[15])
                && isValidNumber(inputTokens[16])
                && isValidNumber(inputTokens[17]);
    }

    private static void buildTotoro(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[15]);
        int y = Integer.parseInt(inputTokens[16]);
        int z = Integer.parseInt(inputTokens[17]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        if(gm.buildTotoro(loc, p))
        {
            OutputAdapter.sendBuiltTotoroMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        }
        else
        {
            OutputAdapter.sendIllegalBuildMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
            game.invalidBuild(tournamentPlayer);
        }
    }

    private static boolean isBuildTigerCommand(String[] inputTokens)
    {
        return inputTokens[11].equals("BUILD")
                && inputTokens[12].equals("TIGER")
                && inputTokens[13].equals("PLAYGROUND")
                && inputTokens[14].equals("AT")
                && isValidNumber(inputTokens[15])
                && isValidNumber(inputTokens[16])
                && isValidNumber(inputTokens[17]);
    }

    private static void buildTiger(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        int x = Integer.parseInt(inputTokens[15]);
        int y = Integer.parseInt(inputTokens[16]);
        int z = Integer.parseInt(inputTokens[17]);

        if(x+y+z != 0)
        {
            game.invalidBuild(tournamentPlayer);
        }

        Location loc=new Location(x, y, z);

        GameManager gm = game.getGameManager();
        Player p = gm.getPlayer(tournamentPlayer.getID());
        if(gm.buildTiger(loc, p))
        {
            OutputAdapter.sendBuiltTigerMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        }
        else
        {
            OutputAdapter.sendIllegalBuildMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
            game.invalidBuild(tournamentPlayer);
        }
    }

    private static boolean isUnableToBuild(String[] inputTokens)
    {
        return inputTokens[11].equals("UNABLE")
                && inputTokens[12].equals("TO")
                && inputTokens[13].equals("BUILD");
    }

    private static void failToBuild(GameThread game, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        OutputAdapter.sendUnableToBuildMessage(game.getPlayersInGame(), tournamentPlayer, inputTokens);
        game.unableToBuild(tournamentPlayer);
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
            default :
                System.err.println("Error, default rotation case hit!");
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

    private static boolean isCorrectTile(String[] inputTokens, Tile tile)
    {
        return inputTokens[5].equals(tile.getStringOfTerrains());
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
