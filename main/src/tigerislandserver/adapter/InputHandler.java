package tigerislandserver.adapter;

public class InputHandler extends InputAdapter
{
    public InputHandler(String input)
    {
        super(input);
    }

    @Override
    public void run()
    {
        if (isEnterTournamentCommand() && verifyTournamentPassword()) {
            enterTournament();
        } else if (isIdentificationComand() && verifyUsernameAndPassword()) {
            registerUser();
        } else if (isGameMoveCommand()) {
            GameMoveInputHandler handler = new GameMoveInputHandler(getInput());
            handler.start();
        }
    }

    private boolean isEnterTournamentCommand()
    {
        return getInputTokens().length == 3
                && getInputTokens()[0].equals("ENTER")
                && getInputTokens()[1].equals("THUNDERDOME");
    }

    private boolean verifyTournamentPassword()
    {
        String password = getInputTokens()[3];

        //TODO
        return password.equals("password");
    }

    private void enterTournament()
    {
        //TODO
    }

    private boolean isIdentificationComand()
    {
        return getInputTokens().length == 4
                && getInputTokens()[0].equals("I")
                && getInputTokens()[1].equals("AM");
    }

    private boolean verifyUsernameAndPassword()
    {
        String username = getInputTokens()[2];
        String password = getInputTokens()[3];

        //TODO
        return username.equals("username")
                && password.equals("password");
    }

    private void registerUser()
    {
        //TODO
    }

    private boolean isGameMoveCommand()
    {
        return getInputTokens().length > 12
                && getInputTokens()[0].equals("GAME")
                && isValidNumber(getInputTokens()[1])
                && getInputTokens()[2].equals("MOVE")
                && isValidNumber(getInputTokens()[3]);
    }
}
