package tigerisland.player;

public class PlayerID
{
    private volatile static int numPlayers = 0;
    private static final Object lock = new Object();
    private final int id;
    private static volatile int playerIDs = 0;
    private static Object lock = new Object();
    private static int pID;

    public PlayerID()
    {
        synchronized(lock) {
            id= ++numPlayers;
        }
    }

    public int getId()
    {
        return id;
    }
}