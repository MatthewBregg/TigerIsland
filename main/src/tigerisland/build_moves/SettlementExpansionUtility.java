package tigerisland.build_moves;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Terrain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
                buildActionData.getSettlementToExpandFromLocation(),
                buildActionData.getPlayer().getId()).getConnectedLocations();

        // The locations we find that area expandable.
        Set<Location> expandableLocs = new HashSet<>();
        // Now check and find all the locations we can expand to.
        for (Location settlementLoc : settlementLocations) {
            for (Location locationSurroundingSettlementLoc : settlementLoc.getSurroundingLocations()) {
                if (settlementLocations.contains(locationSurroundingSettlementLoc)) {
                    continue;
                }
                if (this.locationHasTerrainp(locationSurroundingSettlementLoc,buildActionData.getExpansionTerrain())) {
                    expandableLocs.addAll(this.getConnectedUnoccupiedHexesOfSameTerrain(locationSurroundingSettlementLoc));
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
    public Set<Location> getConnectedUnoccupiedHexesOfSameTerrain(Location loc) {
        Set<Location> connected = new HashSet<>();
        getConnectedUnoccupiedHexesOfSameTerrainImpl(loc,connected);
        return connected;
    }

    private void getConnectedUnoccupiedHexesOfSameTerrainImpl(Location loc, Set<Location> visited) {
        if (visited.contains(loc) || pieceBoard.isLocationOccupied(loc)) {
            return;
        }
        visited.add(loc);

        List<Location> adjacentLocations = loc.getSurroundingLocations();
        for (Location adjLoc : adjacentLocations) {
            if ( locationsTerrainMatchp(loc,adjLoc) && !pieceBoard.isLocationOccupied(adjLoc) ) {
                getConnectedUnoccupiedHexesOfSameTerrainImpl(adjLoc,visited);
            }
        }
    }

    /**
     * If a location is null, If a loc doesn't exist, can't expand from it, to it,
     * so return false.
     * @param locA
     * @param locB
     * @return Returns true if both locations are occupied and have the same terrain, else false.
     */
    private boolean locationsTerrainMatchp(Location locA, Location locB ) {
        if( !hexBoard.isLocationUsed(locA) || !hexBoard.isLocationUsed(locB)){
            return false;
        }
        return hexBoard.getHex(locA).getTerrain().equals(hexBoard.getHex(locB).getTerrain());
    }

    private boolean locationHasTerrainp(Location loc, Terrain t) {
        if ( !hexBoard.isLocationUsed(loc) ) {
            return false;
        }
        return hexBoard.getHex(loc).getTerrain().equals(t);
    }

    public int getVillagersNeededToExpand(BuildActionData buildActionData) {
        class VillagerCountReducer implements Consumer<Location> {
            int villagers = 0;
            @Override
            public void accept(Location location) {
                villagers+= SettlementExpansionUtility.this.getHexLevel(location);
            }

            public int getVillagers() {
                return villagers;
            }
        }
        VillagerCountReducer villagerCountReducer = new VillagerCountReducer();
        mapOverExpandableLocations(buildActionData, villagerCountReducer);
        return villagerCountReducer.getVillagers();
    }

    public int getScoreOnExpansion(BuildActionData buildActionData) {
        class ScoreReducer implements Consumer<Location> {
            int runningScore = 0;
            @Override
            public void accept(Location location) {
                runningScore += Math.pow(SettlementExpansionUtility.this.getHexLevel(location),2);
            }

            public int getRunningScore() {
                return runningScore;
            }
        }
        ScoreReducer scoreReducer = new ScoreReducer();
        mapOverExpandableLocations(buildActionData, scoreReducer);
        return scoreReducer.getRunningScore();
    }

    private void mapOverExpandableLocations(BuildActionData buildActionData, Consumer<Location> consumer) {
        for (Location expandableLoc : this.getExpandableHexes(buildActionData)) {
            consumer.accept(expandableLoc);
        }
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
