package tigerislandserver.adapter;

//TODO THIS CLASS WILL BE DELETED

import tigerislandserver.server.TournamentPlayer;

public abstract class InputAdapter extends Thread
{
    private final String input;
    private final String[] inputTokens;

    public InputAdapter(String input)
    {
        this.input = input;
        this.inputTokens = input.split("\\s+");
    }

    public String getInput()
    {
        return input;
    }

    public String[] getInputTokens()
    {
        return inputTokens;
    }

    protected static boolean isValidNumber(String inputToken)
    {
        return inputToken.matches("^(\\+|-)?\\d+$");
    }

    public static void authenticate(TournamentPlayer tournamentPlayer, String s)
    {
    }
}