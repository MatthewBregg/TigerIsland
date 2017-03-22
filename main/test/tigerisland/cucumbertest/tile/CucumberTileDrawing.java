package tigerisland.cucumbertest.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;


public class CucumberTileDrawing {
    private TileDeck nonEmptyDeck = null;
    private Tile drawnTile = null;
    private int initialSize;
    private int drawnSize;



    @Given("^We have a non-empty tile deck$")
    public void weHaveADeckWithSizeGreaterThanZero() throws Throwable {
        nonEmptyDeck = new TileDeck(1234098);
        assert(nonEmptyDeck.getCount()>0);
        //TODO add the getMaxSize here
        assert(nonEmptyDeck.getCount()==48);
        initialSize = nonEmptyDeck.getCount();
    }

    @When("^A tile is drawn.$")
    public void weDrawATile() throws Throwable {
        nonEmptyDeck.getTile();
        drawnTile = nonEmptyDeck.getTile();
        drawnSize = nonEmptyDeck.getCount();
    }

    @Then("^The tile is removed from the deck.$")
    public void weFindThatTileIsRemoved() throws Throwable {
      //TODO method should check that the original tile is not in the deck
        assert(drawnSize!=initialSize);

    }
}

