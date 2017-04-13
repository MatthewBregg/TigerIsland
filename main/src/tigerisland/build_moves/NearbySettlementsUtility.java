package tigerisland.build_moves;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NearbySettlementsUtility {

    private Location location;
    private SettlementBoard board;
    private Player player;
    PlayerID id;

    public NearbySettlementsUtility(BuildActionData data, SettlementBoard board){
        this.player = data.getPlayer();
        this.board = board;
        this.id = player.getId();
        this.location = data.getHexBuildLocation();

    }

    public List<Settlement> getPossibleSettlementsForBuild(){
        List<Location> locations = location.getSurroundingLocations();
        Set<Settlement> settlements = new ArrayList<>();

        for ( Location loc : locations ) {
            Settlement settlement = board.getSettlement(loc, player.getId());
            if ( settlement.settlementSize() > 0 ) {
                settlements.add(settlement);
            }
        }
        return new ArrayList<Settlement>(settlements);
    }







}
