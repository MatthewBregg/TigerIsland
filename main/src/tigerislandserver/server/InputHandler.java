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
        if(isEnterGame())
        {
            //tryEnterGame()
        }
    }

    private boolean isEnterGame()
    {
        boolean isEnterGame = true;

        isEnterGame &= inputTokens[0].equals("ENTER");
        isEnterGame &= inputTokens[1].equals("THUNDERDOME");

        return isEnterGame;
    }

    private boolean isIdentification()
    {
        boolean isIdentification = true;

        isIdentification &= inputTokens[0].equals("I");
        isIdentification &= inputTokens[1].equals("AM");

        return isIdentification;
    }

    private boolean isGameMove()
    {
        boolean isIdentification = true;

        isIdentification &= inputTokens[0].equals("GAME");

        return isIdentification;
    }
}
