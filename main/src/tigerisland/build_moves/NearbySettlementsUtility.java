package tigerisland.build_moves;

import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;

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
        List<Settlement> settlements = new ArrayList<>();

        boolean hasSettlement;
        Settlement playerNearbySettlement;

        List<Location> previousLocations = new ArrayList<>();
        for (Location location : locations){
            if (previousLocations.contains(location))
                continue;
            hasSettlement = board.isLocationOccupied(location, id);
//            try {
//
//            }
//            catch(Exception e){
//                continue;
//            }
            if (hasSettlement){
                playerNearbySettlement = board.getSettlement(location);
                settlements.add(board.getSettlement(location));
                previousLocations.addAll(playerNearbySettlement.getConnectedLocations());

            }
//            else
//                locations.remove(location);
        }

        return settlements;
    }







}
