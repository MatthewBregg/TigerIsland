package tigerisland.tile;

import tigerisland.terrains.Terrain;

import java.util.Vector;
import java.util.Random;

public class TileDeck {
    private Vector<Tile> tiles;
    private int tileTypes;
    private int countPerTileType;

    public TileDeck(long seed){
        tiles = new Vector<Tile>();
        tileTypes = TileFactory.getTileCombinations();
        countPerTileType = 3;

        Vector<Integer> tileIntegerList = getRandomIntOrder(getMaxDeckSize(), seed);
        initialize(tileIntegerList);
    }

    private Vector<Integer> getRandomIntOrder(int size, long seed){
        // generate numbers to represent each of cards
        Vector<Integer> tempIntegerList = new Vector<Integer>();
        for(int i = 0; i < size; ++i)
            tempIntegerList.add(i, i);

        // create random generator
        Random randomGenerator = new Random(seed);

        // randomize integer order
        Vector<Integer> integerList = new Vector<Integer>();
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
