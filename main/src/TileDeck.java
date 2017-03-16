import java.util.Vector;
import java.util.Random;

public class TileDeck {
    private Vector<Tile> tiles;

    public TileDeck(long seed){
        tiles = new Vector<Tile>();
    }

    private void initialize(long seed){
        /*
        // randomization of numbers 1-48 (0-47)
        // mod(random#, 16) chooses tile type
        // new tile added to tileDeck
        // should this always set the deck to 48 cards?
        */
        int initialDeckSize = 48;

        // generate numbers to represent each of 48 cards
        Vector<Integer> cardInts = new Vector<Integer>();
        for(int i = 0; i < initialDeckSize; ++i)
            cardInts.add(i);

        // create random generator
        Random randomGenerator = new Random(seed);


        // generate cards based on generator
        int cardTypeCount = 16;
        int cardID = 0;
        for(int i = initialDeckSize; i > 0; i--){
            int randInt = randomGenerator.nextInt(i);
            int cardInt = Math.floorMod(randInt, cardTypeCount);

            Tile tempTile = generateTile(cardInt, cardID);
            tiles.add(tempTile);

            cardID++;
        }
    }

    private Tile generateTile(int cardInt, int cardID) {
        // Need to determine terrains
        Terrain leftTerrain = new Terrain();
        Terrain rightTerrain = new Terrain();

        Tile newTile = new Tile(cardID, /* Orientation placeholder */ "left", leftTerrain, rightTerrain);
        return newTile;
    }

    private void shuffleDeck(int seed){
        // TODO: Is this necessary?
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
