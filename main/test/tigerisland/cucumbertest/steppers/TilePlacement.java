package tigerisland.cucumbertest.steppers;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.TestLogger;
import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class TilePlacement implements En {

    private ArrayList<Player> players = new ArrayList<>();
    private Location volcanoLocation = new Location(0,0,0);
    private GameManager manager;


    private void initializePlayers(){
        players.add(new Player());
        players.add(new Player());

        manager = new GameManager(players, new TestLogger());

    }

    boolean result = true;
    public TilePlacement() {
        Given("^The board is not empty, and there is one tile placed somewhere$", () -> {
            initializePlayers();
            Assert.assertTrue(manager.placeTile(new Tile(), volcanoLocation));
        });
        When("^A player attempts to place a tile on top of said tile$", () -> {
            result = manager.placeTile(new Tile(), volcanoLocation);
        });
        Then("^Failure occurs$", () -> {
            Assert.assertFalse(result);
        });
    }
}
