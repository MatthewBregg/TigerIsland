package tigerisland.build;

import tigerisland.board.Location;
import tigerisland.player.Player;
import tigerisland.terrains.Terrain;

public class BuildActionData {

    private Player player;
    private Location hexLocation;
    private Location settlementLocation;
    private Terrain terrain;

    private BuildActionData(Player player, Location hexLocation, Location settlementLocation, Terrain terrain) {
        this.player = player;
        this.hexLocation = hexLocation;
        this.settlementLocation = settlementLocation;
        this.terrain = terrain;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getHexLocation() {
        return hexLocation;
    }

    public Location getSettlementLocation() {
        return settlementLocation;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public static class Builder {

        private Player player;
        private Location hexLocation;
        private Location settlementLocation;
        private Terrain terrain;

       public Builder withPlayer(Player player) {
           this.player = player;
           return this;
       }

       public Builder withHexLocation(Location hexLocation) {
           this.hexLocation = hexLocation;
           return this;
       }

       public Builder withSettlementLocation(Location settlementLocation) {
            this.settlementLocation = settlementLocation;
            return this;
       }

       public Builder withTerrain(Terrain terrain) {
           this.terrain = terrain;
           return this;
       }

       public BuildActionData build() {
          return new BuildActionData(player, hexLocation, settlementLocation, terrain);
       }
    }
}
