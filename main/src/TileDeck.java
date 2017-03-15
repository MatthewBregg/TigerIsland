import java.util.Vector;

public class TileDeck {
    private Vector<Tile> tiles;

    public TileDeck(int seed){
        tiles = new Vector<Tile>();
    }

    public Tile getTile(){
        return null;
    }

    public int getCount(){
        return tiles.size();
    }

    private void shuffleDeck(int seed){
        
    }

    private void addTile(Tile tile){
        tiles.add(tile);
    }

    public boolean isEmpty(){
        return tiles.isEmpty();
    }

}
