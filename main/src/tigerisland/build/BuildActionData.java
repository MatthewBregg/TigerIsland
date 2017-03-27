package tigerisland.build;

import tigerisland.board.Location;
import tigerisland.player.Player;
import tigerisland.terrains.Terrain;

public class BuildActionData {

    private Player player;
    private Location hexBuildLocation;
    private Location settlementToExpandFromLocation;
    private Terrain expansionTerrain;

    private BuildActionData(Player player, Location hexBuildLocation, Location settlementToExpandFromLocation, Terrain expansionTerrain) {
        this.player = player;
        this.hexBuildLocation = hexBuildLocation;
        this.settlementToExpandFromLocation = settlementToExpandFromLocation;
        this.expansionTerrain = expansionTerrain;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getHexBuildLocation() {
        return hexBuildLocation;
    }

    public Location getSettlementToExpandFromLocation() {
        return settlementToExpandFromLocation;
    }

    public Terrain getExpansionTerrain() {
        return expansionTerrain;
    }

    public static class Builder {

        private Player player;
        private Location hexBuildLocation;
        private Location settlementToExpandFromLocation;
        private Terrain expansionTerrain;

       public Builder withPlayer(Player player) {
           this.player = player;
           return this;
       }

       public Builder withHexLocation(Location hexLocation) {
           this.hexBuildLocation = hexLocation;
           return this;
       }

       public Builder withSettlementLocation(Location settlementLocation) {
            this.settlementToExpandFromLocation = settlementLocation;
            return this;
       }

       public Builder withTerrain(Terrain terrain) {
           this.expansionTerrain = terrain;
           return this;
       }

       public BuildActionData build() {
          return new BuildActionData(player, hexBuildLocation, settlementToExpandFromLocation, expansionTerrain);
       }
    }
}
