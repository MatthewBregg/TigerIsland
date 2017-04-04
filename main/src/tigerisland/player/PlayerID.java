package tigerisland.player;

public class PlayerID {
    private static volatile int playerIDs = 0;
    private static Object lock = new Object();
    private static int pID;


    public PlayerID(){
        synchronized(lock) {
            pID= ++playerIDs;
        }
    }

    public int getID(){
        return pID;
    }

}