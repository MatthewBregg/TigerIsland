package tigerisland.tile;

import tigerisland.terrains.*;

import java.util.List;
import java.util.Vector;
import java.util.Random;

public class TileDeck {
    private Vector<Tile> tiles;
    private int tileTypes;
    private int countPerTileType;

    public TileDeck(long seed){
        tiles = new Vector<>();
        tileTypes = TileFactory.getTileCombinations();
        countPerTileType = 3;

        Vector<Integer> tileIntegerList = getRandomIntOrder(getMaxDeckSize(), seed);
        initialize(tileIntegerList);
    }

    public TileDeck(List<String> tiles) {
        // Only call this from testing, the purpose of this is to generate a specific tiledeck config for the purposes of testing!!
        System.err.println("!!!!!!!THIS SHOULD NOT APPEAR IN YOUR CONSOLE, IF IT DOES, FIND OUT WHY AND REMOVE THE CALL TO THIS CONSTRUCTOR");
        tiles = new Vector<>();
        int i = 0;
        for ( String string : tiles ) {
            ++i;
            String[] terrainTypes = string.split("[+]");
            Terrain t1 = getTerrainFromString(terrainTypes[0]);
            Terrain t2 = getTerrainFromString(terrainTypes[1]);
            this.tiles.add(new Tile(i,t1,t2));
        }
    }

    private Terrain getTerrainFromString(String terrainType) {
        if ( "GRASS".equals(terrainType)) {
            return Grassland.getInstance();
        }

        if ( "ROCK".equals(terrainType)) {
            return Rocky.getInstance();
        }

        if ( "JUNGLE".equals(terrainType)) {
            return Jungle.getInstance();
        }

        if ( "LAKE".equals(terrainType)) {
            return Lake.getInstance();
        }

        System.err.println("You mispelled in your tile deck override!");
        return null;
    }

    private Vector<Integer> getRandomIntOrder(int size, long seed){
        // generate numbers to represent each of cards
        Vector<Integer> tempIntegerList = new Vector<>();

        for(int i = 0; i < size; ++i)
            tempIntegerList.add(i, i);

        // create random generator
        Random randomGenerator = new Random(seed);

        // randomize integer order
        Vector<Integer> integerList = new Vector<>();

        for(int i = size; i > 0; i--){
            int randInt = randomGenerator.nextInt(i);
            integerList.add(tempIntegerList.remove(randInt));
        }

        return integerList;
    }

    public int getMaxDeckSize(){
        return (tileTypes * countPerTileType);
    }


    private void initialize(Vector<Integer> tileIntegerList){
        // get tiles from tile factory
        int cardID = 0;
        for(int i = 0; i < tileIntegerList.size(); ++i){
            int cardInt = tileIntegerList.elementAt(i);
            int cardType = Math.floorMod(cardInt, tileTypes);

            addTile(TileFactory.getTile(cardType, cardID));
            cardID++;
        }
    }

    private void addTile(Tile tile){
        tiles.add(tile);
    }

    public Tile drawTile(){
        if(isEmpty())
            return null;
        return tiles.remove(0);
    }

    public int getCount(){
        return tiles.size();
    }

    public boolean isEmpty(){
        return tiles.isEmpty();
    }

}
