import java.util.Vector;
import java.util.Random;

public class TileDeck {
    private Vector<Tile> tiles;
    private int tileTypes;
    private int countPerTileType;

    public TileDeck(long seed){
        tiles = new Vector<Tile>();
        tileTypes = 16;
        countPerTileType = 3;

        initialize(seed);
    }

    private void initialize(long seed){
        tiles.clear();
        int initialDeckSize = getMaxDeckSize();

        // generate numbers to represent each of cards
        Vector<Integer> cardInts = new Vector<Integer>(initialDeckSize);
        for(int i = 0; i < initialDeckSize; ++i)
            cardInts.add(i, i);

        // create random generator
        Random randomGenerator = new Random(seed);


        // generate cards based on generator
        int cardID = 0;
        for(int i = initialDeckSize; i > 0; i--){
            int randInt = randomGenerator.nextInt(i);
            int cardInt = Math.floorMod(randInt, tileTypes);

            Tile tempTile = generateTile(cardInt, cardID);
            tiles.add(tempTile);

            cardID++;
        }
    }

    private int getMaxDeckSize(){
        return (tileTypes * countPerTileType);
    }

    private Tile generateTile(int cardInt, int cardID) {
        // TODO: Need to determine terrains and add orientation
        Terrain leftTerrain = new Terrain();
        Terrain rightTerrain = new Terrain();

        Tile newTile = new Tile(cardID, /* Orientation placeholder */ "left", leftTerrain, rightTerrain);
        return newTile;
    }

    private void addTile(Tile tile){
        tiles.add(tile);
    }

    public Tile getTile(){
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
