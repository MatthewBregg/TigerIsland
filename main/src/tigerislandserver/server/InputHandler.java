package tigerislandserver.server;

public class InputHandler extends Thread
{
    private final String input;
    private final String[] inputTokens;

    public InputHandler(String input)
    {
        this.input = input;
        this.inputTokens = input.split("\\s+");
    }

    @Override
    public void run()
    {
        if(isEnterTournamentCommand() && verifyTournamentPassword())
        {
            enterTournament();
        }
        else if(isIdentificationComand() && verifyUsernameAndPassword())
        {
            registerUser();
        }
        else if(isGameMoveCommand())
        {
            GameMoveInputHandler handler=new GameMoveInputHandler(input);
            handler.start();
        }
    }

    private boolean isEnterTournamentCommand()
    {
        boolean isEnterGame = true;

        isEnterGame &= inputTokens[0].equals("ENTER");
        isEnterGame &= inputTokens[1].equals("THUNDERDOME");

        return isEnterGame;
    }

    private boolean verifyTournamentPassword()
    {
        int passwordStart = inputTokens[0].length() + inputTokens[0].length() + 2; // 5 + 11 + 2 = 18
        String password = input.substring(passwordStart); //This is in

        //TODO
        return password.equals("password");
    }

    private void enterTournament()
    {
        //TODO
    }

    private boolean isIdentificationComand()
    {
        boolean isIdentification = true;

        isIdentification &= inputTokens[0].equals("I");
        isIdentification &= inputTokens[1].equals("AM");

        return isIdentification;
    }

    private boolean verifyUsernameAndPassword()
    {
        int passwordStart = inputTokens[0].length() + inputTokens[0].length() + 2; // 1 + 2 + 2 = 5
        String password = input.substring(passwordStart);

        //TODO
        return password.equals("password");
    }

    private void registerUser()
    {
        //TODO
    }

    private boolean isGameMoveCommand()
    {
        boolean isIdentification = true;

        isIdentification &= inputTokens[0].equals("GAME");

        return isIdentification;
    }
}
