package tigerisland.build;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Terrain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettlementExpansionUtility {
    private HexBoard hexBoard;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;


    public SettlementExpansionUtility(HexBoard hexBoard, PieceBoard pieceBoard, SettlementBoard settlementBoard) {
        this.hexBoard = hexBoard;
        this.pieceBoard = pieceBoard;
        this.settlementBoard = settlementBoard;
    }

    /**
     * Returns all the locations it is valid to expand to.
     * In other words, all locations that are connected to settlement in buildActionData,
     * with a terrain matching the terrain of buildActionData.getHexLocation
     *
     * @param buildActionData
     * @return
     */
    public Set<Location> getExpandableHexes(BuildActionData buildActionData) {
        // Get our settlement, and it's location.
        Set<Location> settlementLocations = settlementBoard.getSettlement(
                buildActionData.getSettlementLocation(),
                buildActionData.getPlayer().getId()).getConnectedLocations();


        // The locations we find that arae expandable.
        Set<Location> expandableLocs = new HashSet<>();
        // Now checkand find all the locations we can expand to.
        for (Location settlementLoc : settlementLocations) {
            for (Location locationSurroundingSettlementLoc : settlementLoc.getSurroundingLocations()) {
                if (settlementLocations.contains(locationSurroundingSettlementLoc)) {
                    continue;
                }
                if (this.locationsTerrainMatchp(locationSurroundingSettlementLoc,buildActionData.getHexLocation())) {
                    expandableLocs.addAll(this.getConnectedUnOccupiedHexesOfSameTerrain(locationSurroundingSettlementLoc));
                }
            }
        }
        return expandableLocs;
    }

    /**
     *  Note that this is player agnostic, a hex of either player will block the hex.
     *  (This is because settlements cannot expand through other settlements.)
     * @param loc The location to find all connected hexes
     * @return The set of all connected hexes, including loc.
     */
    public Set<Location> getConnectedUnOccupiedHexesOfSameTerrain(Location loc) {
        Set<Location> connected = new HashSet<>();
        getConnectedUnOccupiedHexesOfSameTerrainImpl(loc,connected);
        return connected;
    }

    private void getConnectedUnOccupiedHexesOfSameTerrainImpl(Location loc, Set<Location> visited) {
        if ( visited.contains(loc) ) {
            return;
        }

        if ( pieceBoard.isLocationOccupied(loc)) {
            return;
        }
        visited.add(loc);

        List<Location> adjLocs = loc.getSurroundingLocations();
        for (Location adjLoc : adjLocs ) {
            if ( locationsTerrainMatchp(loc,adjLoc) && !pieceBoard.isLocationOccupied(adjLoc) ) {
                getConnectedUnOccupiedHexesOfSameTerrainImpl(adjLoc,visited);

            }
        }
        return;
    }

    /**
     * If a location is null, If a loc doesn't exist, can't expand from it, to it,
     * so return false.
     * @param locA
     * @param locB
     * @return Returns true if both locations are occupied and have the same terrain, else false.
     */
    private boolean locationsTerrainMatchp(Location locA, Location locB ) {

        if ( !hexBoard.isLocationUsed(locA) || !hexBoard.isLocationUsed(locB)) {
            //
            return false;
        }
        return hexBoard.getHex(locA).getTerrain().equals(hexBoard.getHex(locB).getTerrain());
    }

    public int getVillagersNeededToExpand(BuildActionData buildActionData) {
        int villagersNeeded = 0;
        for (Location expandableLoc : this.getExpandableHexes(buildActionData)) {
            villagersNeeded+= this.getHexLevel(expandableLoc);
        }
        return villagersNeeded;
    }

    /**
     * Precondition is location is occupied
     * @param loc
     * @return
     */
    private int getHexLevel(Location loc) {
        return hexBoard.getHex(loc).getLevel();
    }
}
