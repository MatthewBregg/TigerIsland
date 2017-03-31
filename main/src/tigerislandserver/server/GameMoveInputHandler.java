package tigerislandserver.server;

public class GameMoveInputHandler extends Thread
{
    private final String input;
    private final String[] inputTokens;

    public GameMoveInputHandler(String input)
    {
        this.input = input;
        this.inputTokens = input.split("\\s+");
    }

    @Override
    public void run()
    {
        //TODO
    }
}
