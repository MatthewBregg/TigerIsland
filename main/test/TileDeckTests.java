import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileDeckTests {
    @Test
    public void constructorTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertNotEquals(tileDeck, null);
    }

    @Test
    public void initializeTileDeckTest(){
        TileDeck tileDeck = new TileDeck(3);
        assertEquals(tileDeck.getCount(), 48);
    }

    @Test
    public void emptyTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertFalse(tileDeck.isEmpty());
    }
}
