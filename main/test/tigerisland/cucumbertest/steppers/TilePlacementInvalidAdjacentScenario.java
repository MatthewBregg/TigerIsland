package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class TilePlacementInvalidAdjacentScenario implements En {

    Player player1;
    Player player2;
    ArrayList<Player> players;
    GameManager gameManager;

    Board board;
    Tile tile;
    Location location;

    boolean tilePlacementSuccessful = false;

    public TilePlacementInvalidAdjacentScenario() {

        player1 = new Player();
        player2 = new Player();
        players = new ArrayList<>(); players.add(player1); players.add(player2);

        gameManager = new GameManager(players);
        board = gameManager.getHexBoard();

        tile = new Tile();
        location = new Location(45, -45, 0);

        Given("^the board is not empty$", () -> {
            Assert.assertTrue( board.getSize() > 0);
        });

        When("^A player attempts to put a tile down at an unoccupied position$", () -> {
            tilePlacementSuccessful = gameManager.placeTile(tile, location);
        });

        And("^the position does not have any edges touching existing tiles$", () -> {

        });

        Then("^Invalid placement, tile is not placed.$", () -> {
           Assert.assertFalse(tilePlacementSuccessful);
        });
    }
}
