package tigerisland.tile;

import tigerisland.hex.Hex;
import tigerisland.terrains.*;

public class TileFactory {
    final static Terrain[] terrainTypes = new Terrain[]{ Rocky.getInstance(), Grassland.getInstance(), Lake.getInstance(), Jungle.getInstance() };

    private TileFactory() {}

    private static Tile generateTile(int desiredTileType, int newTileID) {
        int id = 0;
        for (Terrain i : terrainTypes) {
            for (Terrain j : terrainTypes) {
                if (id == desiredTileType) {
                    return (new Tile(newTileID, new Hex(i), new Hex(j)));
                }
                ++id;
            }
        }
        return null;
    }

    public static Tile getTile(int tileType, int newTileID) {
        if ( tileType < 0 || tileType > (getTileCombinations() - 1)) {
            throw new IndexOutOfBoundsException("Can only generate 0-15 unique tiles, rotate for more");
        }
       return generateTile(tileType, newTileID);
    }

    public static int getTileCombinations() {
        int tileTypesCount = (terrainTypes.length * terrainTypes.length);
        return tileTypesCount;
    }
}
