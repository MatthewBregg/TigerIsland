package tigerisland.settlement;

import tigerisland.player.PlayerID;

public class CreatePlayerID {
    private static PlayerID pID = null;
    public static PlayerID createPlayerID() {
        if ( pID == null ) {
            pID = new PlayerID();
        }
        return pID;
    }
}
