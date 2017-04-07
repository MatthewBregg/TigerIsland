package tigerislandserver.tournament;

import tigerisland.player.PlayerID;
import tigerislandserver.gameplay.GameThread;

import java.util.HashMap;

public class Tournament
{
    private static final Object lock = new Object();
    private static volatile Tournament instance;

    private HashMap<PlayerID, GameThread> games;

    public static Tournament getInstance()
    {
        Tournament tournament = instance;
        if (tournament == null)
        {
            synchronized (lock) //We don't need to synchronize every time, just the first few until it is initialized
            {
                tournament = instance;

                if (tournament == null)
                {
                    tournament = new Tournament();
                    instance = tournament;
                }
            }
        }

        return tournament;
    }

    private Tournament()
    {
        games=new HashMap<PlayerID, GameThread>();
    }
}
