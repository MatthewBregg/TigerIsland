package tigerislandserver.adapter;

import tigerislandserver.TournamentVariables;
import tigerislandserver.server.TextFileReader;
import tigerislandserver.server.TournamentPlayer;

import java.util.HashMap;

public class InputAdapter
{
    private static HashMap<String, String> unusedUsernames = getUserNamesAndPasswords();

    private static HashMap<String, String> getUserNamesAndPasswords()
    {
        return new TextFileReader(TournamentVariables.getInstance().getUsernamePasswordFileName()).getUsernameAndPasswordCombos();
    }

    public static boolean canEnterTournament(String input)
    {
        return isEnterTournamentCommand(input) && verifyTournamentPassword(input);
    }

    public static boolean authenticate(TournamentPlayer tournamentPlayer, String input)
    {
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
        String password = inputTokens[3];

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
            return unusedUsernames.remove(username, password);
        }
    }
}
