package tigerislandserver.gameplay;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchupTest {
    Matchup matchup;

    @Before
    public void setup(){
        matchup = new Matchup(15, 37);

        assertEquals(15, matchup.getPlayer1Index());
        assertEquals(37, matchup.getPlayer2Index());
    }


    @Test
    public void constructorTest() throws Exception {
    }
}