package tigerislandserver.adapter;

import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class OutputAdapter extends Thread
{
    public OutputAdapter(){}

    public static void sendMoveRequestMessage(TournamentPlayer tournamentPlayer, char gid, int moveNumber, Tile tile)
    {
        tournamentPlayer.sendMessage("MAKE YOUR MOVE IN GAME " + gid + " WITHIN 1.5 SECONDS: MOVE "
                                             + moveNumber + " PLACE " + tile.getStringOfTerrains());
    }

    public static void sendFoundedSettlementMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " PLACED ";
            message += appendBetween(inputTokens, 5, 10);
            message += " FOUNDED ";
            message += appendBetween(inputTokens, 12, 16);

            tp.sendMessage(message);
        }
    }

    public static void sendExpandedSettlementMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " PLACED ";
            message += appendBetween(inputTokens, 5, 10);
            message += " EXPANDED ";
            message += appendBetween(inputTokens, 12, 17);

            tp.sendMessage(message);
        }
    }

    public static void sendBuiltTotoroMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " PLACED ";
            message += appendBetween(inputTokens, 5, 10);
            message += " BUILT ";
            message += appendBetween(inputTokens, 12, 17);

            tp.sendMessage(message);
        }
    }

    public static void sendBuiltTigerMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " PLACED ";
            message += appendBetween(inputTokens, 5, 10);
            message += " BUILT ";
            message += appendBetween(inputTokens, 12, 17);

            tp.sendMessage(message);
        }
    }

    public static void sendIllegalTilePlacementMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " FORFEITED: ILLEGAL TILE PLACEMENT";

            tp.sendMessage(message);
        }
    }

    public static void sendIllegalBuildMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " FORFEITED: ILLEGAL BUILD";

            tp.sendMessage(message);
        }
    }

    public static void sendTimeoutMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " FORFEITED: TIMEOUT";

            tp.sendMessage(message);
        }
    }

    public static void sendUnableToBuildMessage(ArrayList<TournamentPlayer> players, TournamentPlayer tournamentPlayer, String[] inputTokens)
    {
        for(TournamentPlayer tp: players)
        {
            String message = "";
            message += appendBetween(inputTokens, 0, 3);
            message += " PLAYER " + tournamentPlayer.getID().getId();
            message += " LOST: UNABLE TO BUILD";

            tp.sendMessage(message);
        }
    }

    private static String appendBetween(String[] inputTokens, int startIndex, int endIndex)
    {
        String message="";

        for(int i = startIndex; i <= endIndex; i++)
        {
            message += inputTokens[i]+ " ";
        }

        return message.substring(0, message.length()-1);
    }

    public static void requestAuthentication(TournamentPlayer tournamentPlayer)
    {
        tournamentPlayer.sendMessage("TWO SHALL ENTER, ONE SHALL LEAVE");
    }

    public static void sendWaitForTournamentMessage(TournamentPlayer tournamentPlayer)
    {
        tournamentPlayer.sendMessage("WAIT FOR THE TOURNAMENT TO BEGIN " + tournamentPlayer.getID().getId());
    }

    public static void sendWaitForChallengeMessage(TournamentPlayer tournamentPlayer)
    {
        tournamentPlayer.sendMessage("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
    }

    public static void sendEndOfChallengesMessage(TournamentPlayer tournamentPlayer)
    {
        tournamentPlayer.sendMessage("END OF CHALLENGES");
    }

    public static void sendNewChallengeMessage(TournamentPlayer tournamentPlayer, int cid, int numRounds)
    {
        String message ="NEW CHALLENGE  " + cid + " YOU WILL PLAY " + numRounds;
        if(numRounds == 1)
        {
            message += " MATCH";
        }
        else
        {
            message += " MATCHES";
        }

        tournamentPlayer.sendMessage(message);
    }

    public static void sendStartRoundMessage(TournamentPlayer tournamentPlayer, int round, int totalRounds)
    {
        tournamentPlayer.sendMessage("BEGIN ROUND " + round + " OF " + totalRounds);
    }

    public static void sendEndRoundMessage(TournamentPlayer tournamentPlayer, int round, int totalRounds)
    {
        tournamentPlayer.sendMessage("END OF ROUND " + round + " OF " + totalRounds + " WAIT FOR NEXT MATCH");
    }

    public static void sendEndOfAllRoundMessage(TournamentPlayer tournamentPlayer, int round, int totalRounds)
    {
        tournamentPlayer.sendMessage("END OF ROUND " + round + " OF " + totalRounds);
    }

    public static void sendStartMatchMessage(TournamentPlayer tournamentPlayer, TournamentPlayer opponent)
    {
        tournamentPlayer.sendMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponent.getID().getId());
    }

    public static void sendEndMatchMessage(TournamentPlayer tournamentPlayer, TournamentPlayer opponent, int gid, int myScore, int opponentScore)
    {
        String message = "GAME "+gid;
        message += " OVER";
        message += "PLAYER " + tournamentPlayer.getID().getId() + " " + myScore;
        message += "PLAYER " + opponent.getID().getId() + " " + opponentScore;

        tournamentPlayer.sendMessage(message);
    }
}
