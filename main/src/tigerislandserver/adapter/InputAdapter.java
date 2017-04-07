package tigerislandserver.adapter;

import tigerislandserver.server.TournamentPlayer;

public class InputAdapter
{
    public static boolean canEnterTournament(String input)
    {
        return isEnterTournamentCommand(input) && verifyTournamentPassword(input);
    }

    public static boolean authenticate(String input)
    {
       return isIdentificationComand(input) && verifyUsernameAndPassword(input);
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

        //TODO
        return password.equals("password");
    }

    private static boolean isIdentificationComand(String input)
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

        //TODO
        return username.equals("username")
                && password.equals("password");
    }
}
