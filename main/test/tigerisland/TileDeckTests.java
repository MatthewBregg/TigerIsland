package tigerisland;

import org.junit.Assert;
import org.junit.Test;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;

import java.util.Vector;

import static org.junit.Assert.*;

public class TileDeckTests {
    @Test
    public void constructorTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        Assert.assertNotEquals(tileDeck, null);
    }

    @Test
    public void initializeTileDeckTest(){
        TileDeck tileDeck = new TileDeck(3);
        Assert.assertEquals(tileDeck.getCount(), 48);
    }

    @Test
    public void emptyTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertFalse(tileDeck.isEmpty());
    }

    @Test
    public void getTileFromDeckTest(){
        TileDeck tileDeck = new TileDeck(3);
        int initialDeckSize = tileDeck.getCount();
        Vector<Tile> drawnTiles = new Vector<Tile>();

        for(int i = 0; i < initialDeckSize; ++i){
            drawnTiles.add(tileDeck.drawTile());
        }

        int drawnTileIndex = 0;
        for(int i = 0; i < drawnTiles.size(); ++i){
            drawnTileIndex = i;
            if(drawnTiles.elementAt(i) == null)
                break;
        }

        assertEquals(tileDeck.getCount(), 0);
        assertEquals(drawnTiles.size(), 48);
        assertEquals(drawnTileIndex + 1, 48);
    }

    @Test
    public void correctNumberOfTileTypesTest(){
        TileDeck tileDeck = new TileDeck(756);
        int initialDeckSize = tileDeck.getCount();
        Vector<Tile> allTiles = new Vector<Tile>();

        for(int i = 0; i < initialDeckSize; ++i)
            allTiles.add(tileDeck.drawTile());

        Vector<Vector<Tile>> tileArray = new Vector<Vector<Tile>>();

        for(Tile t : allTiles){
            if(!tileArray.isEmpty()) {
                for(Vector<Tile> array : tileArray){
                    if(array.elementAt(0).equals(t)){
                        array.add(t);
                        t = null;
                        break;
                    }
                }
            }
            if(t != null) {
                Vector<Tile> tempArray = new Vector<Tile>();
                tempArray.add(t);
                tileArray.add(tempArray);
            }
        }

        assertFalse(tileArray.isEmpty());
        assertEquals(tileArray.elementAt(0).size(), 3);
        assertEquals(tileArray.size(), 16);
        assertEquals(tileArray.lastElement().size(), 3);
    }
}
