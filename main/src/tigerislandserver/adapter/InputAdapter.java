package tigerislandserver.adapter;

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
}