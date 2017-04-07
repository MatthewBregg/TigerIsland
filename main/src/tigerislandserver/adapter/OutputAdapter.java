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

    public static void sendWaitForChallengeMessage(ArrayList<TournamentPlayer> players)
    {
        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
        }
    }

    public static void sendEndOfChallengesMessage(ArrayList<TournamentPlayer> players)
    {
        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage("END OF CHALLENGES");
        }
    }

    public static void sendNewChallengeMessage(ArrayList<TournamentPlayer> players, int cid, int numRounds)
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

        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage(message);
        }
    }

    public static void sendStartRoundMessage(ArrayList<TournamentPlayer> players, int round, int totalRounds)
    {
        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage("BEGIN ROUND " + round + " OF " + totalRounds);
        }
    }

    public static void sendEndRoundMessage(ArrayList<TournamentPlayer> players, int round, int totalRounds)
    {
        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage("END OF ROUND " + round + " OF " + totalRounds + " WAIT FOR NEXT MATCH");
        }
    }

    public static void sendEndOfAllRoundMessage(ArrayList<TournamentPlayer> players, int round, int totalRounds)
    {
        for(TournamentPlayer tournamentPlayer: players)
        {
            tournamentPlayer.sendMessage("END OF ROUND " + round + " OF " + totalRounds);
        }
    }

    public static void sendStartMatchMessage(TournamentPlayer player1, TournamentPlayer player2)
    {
        player1.sendMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + player2.getID().getId());
        player2.sendMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + player1.getID().getId());
    }

    public static void sendEndGameMessage(TournamentPlayer player1, TournamentPlayer player2, int gid, String p1Score, String p2Score)
    {
        String message = "GAME "+gid;
        message += " OVER";
        message += " PLAYER " + player1.getID().getId() + " " + p1Score;
        message += " PLAYER " + player2.getID().getId() + " " + p2Score;

        player1.sendMessage(message);
        player2.sendMessage(message);
    }
}
