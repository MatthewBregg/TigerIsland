import java.util.Vector;

public class TileDeck {
    private Vector<Tile> tiles;

    public TileDeck(int seed){
        tiles = new Vector<Tile>();
    }

    private void initialize(){
        // TODO: May be unnecessary
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
