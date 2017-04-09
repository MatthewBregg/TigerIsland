package tigerisland.cucumbertest.steppers;

import cucumber.api.java8.En;
import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.Location;
import tigerisland.game.GameManager;
import tigerisland.player.Player;
import tigerisland.tile.Tile;

import java.util.ArrayList;

public class TilePlacementDiffererentLevelHexesScenario implements En {

    Player player1;
    Player player2;
    ArrayList<Player> players;
    GameManager gameManager;

    Board board;
    Tile tile;
    Location location;

    boolean tilePlacementSuccessful = false;

    public TilePlacementDiffererentLevelHexesScenario() {

        player1 = new Player();
        player2 = new Player();
        players = new ArrayList<>(); players.add(player1); players.add(player2);

        gameManager = new GameManager(players);

        String fileName = "";
        board = gameManager.getHexBoard();

        tile = new Tile();
        location = new Location(45, -45, 0);

        Given("^the board is not empty scenario:one.$", () -> {


            Assert.assertTrue(board.getSize() > 0);
        });

        When("^A player attempts to put a tile down at an unoccupied position scenario:one.$", () -> {
            tilePlacementSuccessful = gameManager.placeTile(tile, location);
        });

        And("^one or more of the hexes would not be at the same level of the other tile hexes at that position scenario:one.$", () -> {

        });

        Then("^Tile placement fails scenario:one.$", () -> {
           Assert.assertFalse(tilePlacementSuccessful);
        });
    }
}
