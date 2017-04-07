package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;

import java.util.ArrayList;

public class TotoroBuildOnVolcanoInvalid implements En {


    private ArrayList<Player> players = new ArrayList<>();

    private GameManager manager;
    Location build = new Location(0,0,0);


    private void initializePlayers(){
        players.add(new Player());
        players.add(new Player());

        manager = new GameManager(players);

    }


    public TotoroBuildOnVolcanoInvalid() {
        Given("^Player has a  totoro$", () -> {
            initializePlayers();
            Assert.assertTrue(players.get(0).getTotoroCount()>0);
        });
        And("^is attempting a build action$", () -> {
           //TODO maybe add a part of turn checker later
        });
        When("^Player attempts to place totoro onto a hex with a volcano.$", () -> {
            Assert.assertFalse(manager.buildTotoro(build, players.get(0)));
        });
        Then("^Invalid build action$", () -> {
            //TODO is there a need to assert anything here if done in when?
        });
    }
}
