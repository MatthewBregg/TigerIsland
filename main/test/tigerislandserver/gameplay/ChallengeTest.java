package tigerislandserver.gameplay;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tigerisland.tile.Tile;
import tigerislandserver.server.TournamentClient;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Philip on 4/1/2017.
 */
public class ChallengeTest {
    public static ArrayList<TournamentClient> players;
    Challenge tourneyChallenge;

    @BeforeClass
    public static void suiteSetUp() throws Exception {
        players = new ArrayList<TournamentClient>();
        for(int i = 0; i < 10; ++i){
            players.add(new TournamentClient(new Socket()));
        }
    }

    @Before
    public void testSetup() throws Exception {
        tourneyChallenge = new Challenge(players);
    }

    @Test
    public void setMatchupType() throws Exception {
    }

    @Test
    public void playRound() throws Exception {
    }

    @Test
    public void setupRound() throws Exception {
        tourneyChallenge.setupRound();
        ArrayList<Tile> deck = tourneyChallenge.currentDeck;
        assertEquals(48, deck.size());
    }

    @Test
    public void getCurrentRound() throws Exception {
    }

    @Test
    public void getRoundsRemaining() throws Exception {
    }

}