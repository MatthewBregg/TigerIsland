package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.player.Player;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;

import java.util.ArrayList;

public class GameManagerTest {
    private GameManager manager;

    TileDeck deck = new TileDeck(12341298);

    @Before
    public void setUp() throws Exception {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player());
        players.add(new Player());

        manager = new GameManager(players);
    }

    @Test
    public void test_PlaceTileShouldBeTrueNukingStartingHex() {
        Location location = new Location(0,0,0);

        Tile toPlace = deck.drawTile();
        toPlace.setOrientation(Orientation.getEast());

        Assert.assertTrue(manager.placeTile(toPlace, location));

    }

    @Test
    public void test_PlaceTileShouldBeTruePlacingNextToStartingHex() {
        Tile tile = new Tile();
        Location location = new Location(-1,1,0);


        tile.setOrientation(Orientation.getSouthWest());

        Assert.assertTrue(manager.placeTile(tile, location));

    }

    @Test
    public void expandSettlement() throws Exception {

    }

    @Test
    public void buildTotoro() throws Exception {

    }

    @Test
    public void buildTiger() throws Exception {

    }

    @Test
    public void endGame() throws Exception {

    }

    @Test
    public void returnResults() throws Exception {

    }

}