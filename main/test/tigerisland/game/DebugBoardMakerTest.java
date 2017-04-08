package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tigerisland.board.Location;

import java.util.HashSet;
import java.util.Set;

public class DebugBoardMakerTest {

    private DebugBoardMaker debug = new DebugBoardMaker("../Users/josh/Documents/gitRepos/CEN3031/TigerIsland/main/src/tigerisland/game/DebugBoard.txt");



    @Before
    public void setUp() throws Exception {

    }

    @Ignore ("DebugBoardMaker path needs to be changed to path on your machine")
    @Test

    public void testFirstRow() {

        Set<Location> locations = new HashSet<>();

        locations.add(new Location(0,0,0));
        locations.add(new Location(1,1,-2));

        Assert.assertEquals(debug.getHexMap().keySet(), locations);


    }





}