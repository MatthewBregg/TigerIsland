package tigerislandserver.adapter;

import tigerisland.score.ScoreManager;
import tigerislandserver.TournamentVariables;
import tigerislandserver.server.TextFileReader;
import tigerislandserver.server.TournamentPlayer;

import java.util.HashMap;

public class InputAdapter
{
    private static HashMap<String, String> unusedUsernames = getUserNamesAndPasswords();

    private static HashMap<String, String> getUserNamesAndPasswords()
    {
        //return new HashMap<>();
        return new TextFileReader(TournamentVariables.getInstance().getUsernamePasswordFileName()).getUsernameAndPasswordCombos();
    }

    public static boolean canEnterTournament(String input)
    {
        System.out.println("CLIENT: " + input);
        return isEnterTournamentCommand(input) && verifyTournamentPassword(input);
    }

    public static boolean authenticate(TournamentPlayer tournamentPlayer, String input)
    {
        System.out.println("CLIENT " + tournamentPlayer.getID().getId() + ":" + input);
        boolean success = isIdentificationCommand(input) && verifyUsernameAndPassword(input);

        String[] inputTokens = input.split("\\s+");

        if(success)
        {
            tournamentPlayer.setUsername(inputTokens[2]);
        }

        return success;
    }

    private static boolean isEnterTournamentCommand(String input)
    {
        String[] inputTokens = input.split("\\s+");
        return inputTokens.length == 3
                && inputTokens[0].equals("ENTER")
                && inputTokens[1].equals("THUNDERDOME");
    }

    private static boolean verifyTournamentPassword(String input)
    {
        String[] inputTokens = input.split("\\s+");
        String password = inputTokens[2];

        return password.equals(TournamentVariables.getInstance().getTournamentPassword());
    }

    private static boolean isIdentificationCommand(String input)
    {
        String[] inputTokens = input.split("\\s+");

        return inputTokens.length == 4
                && inputTokens[0].equals("I")
                && inputTokens[1].equals("AM");
    }

    private static boolean verifyUsernameAndPassword(String input)
    {
        String[] inputTokens = input.split("\\s+");

        String username = inputTokens[2];
        String password = inputTokens[3];

        synchronized (unusedUsernames)
        {
            //return true;
            return unusedUsernames.remove(username, password);
        }
    }

    public static void sendScoreCheckMessage(char gameId, TournamentPlayer p1, TournamentPlayer p2, ScoreManager scoreManager){
        int p1score = scoreManager.getPlayerScore(p1.getID());
        int p2score = scoreManager.getPlayerScore(p2.getID());

        p1.sendMessage("GAME " + gameId + " OVER PLAYER " + p1.getID() +" "+ p1score + " PLAYER " + p2.getID() + " " + p2score);

    }
}
