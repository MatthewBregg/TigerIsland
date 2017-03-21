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
}
