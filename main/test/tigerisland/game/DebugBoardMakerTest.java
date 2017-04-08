package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;

import java.util.HashSet;
import java.util.Set;

public class DebugBoardMakerTest {

    private DebugBoardMaker debug = new DebugBoardMaker("./main/src/tigerisland/game/DebugBoard.txt");


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