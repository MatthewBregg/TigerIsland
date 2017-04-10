package tigerisland.player;

public class PlayerID
{
    private volatile static int numPlayers = 0;
    private static final Object lock = new Object();
    private final int id;

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

    public String toString(){
        return ""+id;
    }
}