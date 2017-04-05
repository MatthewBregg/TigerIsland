package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;

import java.util.HashSet;
import java.util.Set;

public class CucumberDeckInitialization implements En {
    TileDeck tileDeck = null;

    public CucumberDeckInitialization() {
        When("^The deck is initialized$", () -> {
            tileDeck = new TileDeck(0);

        });
        Then("^The deck contains (\\d+) tiles, (\\d+) unique combinations$", (Integer tilesTotal, Integer uniqueTotal) -> {
            assert(tileDeck.getCount()==48);
            Set<String> tileSet = new HashSet<>();
            for ( int i = 0; i != 48; ++i ) {
                Tile t = tileDeck.drawTile();
                tileSet.add((t.getLeftHex().getTerrain().toString()
                        + t.getRightHex().getTerrain().toString()
                        + t.getReferenceHex().getTerrain()).toString());
            }
            Assert.assertTrue(tileSet.size() == 16);
            // throw new PendingException();

        });
        Given("^We have no deck$", () -> {
            Assert.assertTrue(tileDeck == null);
        });
    }
}