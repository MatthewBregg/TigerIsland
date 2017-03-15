import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TileDeckTests {
    @Test
    public void createTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertNotEquals(tileDeck, null);
    }

    @Test
    public void newTileDeckZeroCountTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertEquals(tileDeck.getCount(), 0);
        assertTrue(tileDeck.isEmpty());
    }

    @Test
    public void newEmptyTileDeck(){
        TileDeck tileDeck = new TileDeck(0);
        assertTrue(tileDeck.isEmpty());
    }
}
