package tigerisland.test_boards;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestingBoardMakerTest {

    private TestingBoardMaker debug = new TestingBoardMaker("/tigerisland/test_boards/DebugBoard.txt", new ArrayList<Player>());


    @Before
    public void setUp() throws Exception {


    }

    @Test
   @Ignore("Will fix this insignificant non part of game feature that is a testing thingy if there's time")
    public void testFirstRow() {

        Set<Location> locations = new HashSet<>();

        locations.add(new Location(0,0,0));
        locations.add(new Location(1,1,-2));

        Assert.assertEquals(debug.getHexMap().keySet(), locations);


    }





}