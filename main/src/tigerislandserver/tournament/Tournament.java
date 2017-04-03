package tigerislandserver.tournament;

import tigerisland.player.PlayerID;
import tigerislandserver.command.FoundSettlementCommand;
import tigerislandserver.command.PlaceTileCommand;
import tigerislandserver.gameplay.Game;

import java.util.HashMap;

public class Tournament
{
    private static final Object lock = new Object();
    private static volatile Tournament instance;

    private HashMap<PlayerID, Game> games;

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
        games=new HashMap<PlayerID, Game>();
    }
}
