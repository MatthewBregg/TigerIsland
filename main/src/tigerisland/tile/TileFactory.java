package tigerisland.tile;

import tigerisland.hex.Hex;
import tigerisland.terrains.*;

public class TileFactory {
    final static Terrain[] terrainTypes = new Terrain[]{ Rocky.getInstance(), Grassland.getInstance(), Lake.getInstance(), Jungle.getInstance() };

    private TileFactory() {};

    private static Tile generateTile(int desired_id) {
        int id = 0;
        for (Terrain i : terrainTypes) {
            for (Terrain j : terrainTypes) {
                if (id == desired_id) {
                    return (new Tile(id, new Hex(), new Hex(), new Hex()));
                }
                ++id;
            }
        }
        return null;
    }

    public static Tile getTile(int i) {
        if ( i < 0 || i > 15) {
            throw new IndexOutOfBoundsException("Can only generate 0-15 unique tiles, rotate for more");
        }
       return generateTile(i);
    }

    public static int getTileCombinations() {
        int tileTypesCount = terrainTypes.length ^ 2;
        return tileTypesCount;
    }
}
