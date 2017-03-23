package tigerisland.settlement;

import tigerisland.player.PlayerID;

public class CreatePlayerID {
    private static PlayerID pID = null;
    private static PlayerID pID1 = null;
    private static PlayerID pID2 = null;

    public static PlayerID getPlayerID() {
        if ( pID == null ) {
            pID = new PlayerID();
        }
        return pID;
    }
    
    public static PlayerID getP1() {
        if ( pID1 == null ) {
            pID1 = new PlayerID();
        }
        return pID1;
    }
    
    public static PlayerID getP2() {
        if ( pID2 == null ) {
            pID2 = new PlayerID();
        }
        return pID2;
    }
}
