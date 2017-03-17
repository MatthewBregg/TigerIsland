package tigerisland;

import java.util.ArrayList;

public class TileFactory {
    final static Terrain[] terrainTypes = new Terrain[]{ Rocky.getInstance(), Grassland.getInstance(), Lake.getInstance(), Jungle.getInstance() };

    private TileFactory() {};

    private static Tile generateTile(int desired_id) {
        int id = 0;
        for (Terrain i : terrainTypes) {
            for (Terrain j : terrainTypes) {
                ++id;
                if (id == desired_id) {
                    return (new Tile(id, "", i, j));
                }
            }
        }
    }

    public static Tile getTile(int i) {
        if ( i < 0 || i > 15) {
            throw new IndexOutOfBoundsException("Can only generate 0-15 unique tiles, rotate for more");
        }
       return generateTile(i);
    }
}
