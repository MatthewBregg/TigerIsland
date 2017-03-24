package tigerisland.cucumbertest.deck;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;


public class CucumberTileDrawing {
    private TileDeck nonEmptyDeck = null;
    private Tile drawnTile = null;
    private int initialSize;
    private int drawnSize;




    @Given("^We have non-empty tile deck$")
    public void weHaveNonEmptyTileDeck() throws Throwable {
        nonEmptyDeck = new TileDeck(1234098);
        Assert.assertTrue(nonEmptyDeck.getCount()>0);
        //TODO add the getMaxSize here
        Assert.assertTrue(nonEmptyDeck.getCount()==nonEmptyDeck.getMaxDeckSize());
        initialSize = nonEmptyDeck.getCount();
    }

    @When("^A tile is drawn$")
    public void weDrawATile() throws Throwable {
        nonEmptyDeck.drawTile();
        drawnTile = nonEmptyDeck.drawTile();
        drawnSize = nonEmptyDeck.getCount();
    }

    @Then("^The tile is removed from the deck.$")
    public void weFindThatTileIsRemoved() throws Throwable {
      //TODO method should check that the original tile is not in the deck
        Assert.assertTrue(drawnSize!=initialSize);


    }


}

