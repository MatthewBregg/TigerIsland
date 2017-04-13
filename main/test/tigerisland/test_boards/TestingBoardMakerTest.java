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

@Ignore
public class TestingBoardMakerTest {

    private TestingBoardMaker debug = new TestingBoardMaker("/main/resources/DebugBoard.txt", new ArrayList<Player>());


    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testFirstRow() {

        Set<Location> locations = new HashSet<>();

        locations.add(new Location(0,0,0));
        locations.add(new Location(1,1,-2));

        Assert.assertEquals(debug.getHexMap().keySet(), locations);


    }





}