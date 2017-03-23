package tigerisland.cucumbertest.deck;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;


public class CucumberDeckFinite {
    private TileDeck deck = null;
    private Tile drawnTile = null;
    private int initialSize;
    private int drawnSize;



    @Given("^Forty Eight Tiles have been drawn and there are no more tiles remaining$")
    public void weHaveAnEmptyDeck () throws Throwable {
        deck = new TileDeck(1234098);

        for (int i = deck.getMaxDeckSize(); i>0; --i){
            deck.drawTile();
        }

        assert(deck.getCount()==0);

    }

    @When("^A tile is attempted to be drawn.$")
    public void weDrawATile() throws Throwable {
        deck.drawTile();
        assert(deck.getCount()==0);
    }

    @Then("^No tiles are available to be drawn, a tile is not drawn. The game is over.$")
    public void weFindThatTileIsRemoved() throws Throwable {
        drawnTile = deck.drawTile();
        assert(drawnTile==null);
        //TODO add end game condition
    }
}

