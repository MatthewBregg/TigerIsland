package tigerisland.cucumbertest.steppers;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class PieceBuildOnVolcanoInvalid implements En {



    private ArrayList<Player> players = new ArrayList<>();
    private Location volcanoLocation = new Location(0,0,0);
    private GameManager manager;


    private void initializePlayers(){
        players.add(new Player());
        players.add(new Player());

        manager = new GameManager(players);

    }


    public PieceBuildOnVolcanoInvalid() {
        Given("^Player has a  totoro$", () -> {
            initializePlayers();
            Assert.assertTrue(players.get(0).getTotoroCount()>0);
        });
        And("^is attempting a build action$", () -> {
           //TODO maybe add a part of turn checker later
        });
        When("^Player attempts to place totoro onto a hex with a volcano.$", () -> {
            Assert.assertFalse(manager.buildTotoro(volcanoLocation, players.get(0)));
        });
        Then("^Invalid build action$", () -> {
            //TODO is there a need to assert anything here if done in when?
        });
        Given("^Player has a  tiger$", () -> {
            initializePlayers();
            Assert.assertTrue(players.get(0).getTigerCount()>0);
        });
        When("^Player attempts to place tiger onto a hex with a volcano on said tile$", () -> {
            Assert.assertFalse(manager.buildTiger(volcanoLocation,players.get(0)));
        });

        And("^there is a tile of level three or more$", () -> {
            for ( int i = 0; i != 3; ++i ) {
               Assert.assertTrue("On try " + i,  manager.placeTile(new Tile(), volcanoLocation));
            }


            throw new PendingException();
        });
        Given("^Player has a  villager$", () -> {
            initializePlayers();
            Assert.assertTrue(players.get(0).getVillagerCount()>0);
        });
        When("^Player attempts to place villager onto a hex with a volcano on said tile$", () -> {
            Assert.assertFalse(manager.foundSettlement(volcanoLocation,players.get(0)));
        });
    }
}
