import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class TileDeckTests {
    @Test
    public void createTileDeckTest(){
        TileDeck tileDeck = new TileDeck(0);
        assertNotEquals(tileDeck, null);
    }
}
