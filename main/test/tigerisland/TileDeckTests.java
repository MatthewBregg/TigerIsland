package tigerisland;

import org.junit.Assert;
import org.junit.Test;
import tigerisland.tile.TileDeck;

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
}
