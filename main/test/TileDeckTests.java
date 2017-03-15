import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TileDeckTests {
    @Test
    public void createTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertNotEquals(tileDeck, null);
    }

    @Test
    public void newTileDeckIsEmptyTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertEquals(tileDeck.getCount(), 0);
    }
}
