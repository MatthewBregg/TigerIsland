package tigerisland.cucumbertest.deck;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;


public class CucumberDeckFinite {
    private TileDeck deck = new TileDeck(0);
    private Tile drawnTile = null;
    private int initialSize;
    private int drawnSize;



    @When("^A tile is attempted to be drawn.$")
    public void weDrawATile() throws Throwable {
        drawnTile = deck.drawTile();
    }

    @Then("^No tiles are available to be drawn, a tile is not drawn. The game is over.$")
    public void weFindThatTileIsRemoved() throws Throwable {
        Assert.assertTrue(drawnTile==null);
        //TODO add end game condition
    }


    @Given("^Forty Eight Tiles have been drawn and there are no more tiles remaining$")
    public void fortyEightTilesHaveBeenDrawnAndThereAreNoMoreTilesRemaining() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        deck = new TileDeck(0);
        for ( int i = 0; i != 48; ++i ) {
            deck.drawTile();
        }
    }
}

