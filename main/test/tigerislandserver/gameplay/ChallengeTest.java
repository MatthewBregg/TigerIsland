package tigerislandserver.gameplay;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import tigerislandserver.JavaFXScoreboardPOC.RoundInfo;
import tigerislandserver.server.TournamentPlayer;

import java.net.Socket;
import java.util.ArrayList;

@Ignore("Challenge test ignored-- incomplete anyway")
public class ChallengeTest {
    public static ArrayList<TournamentPlayer> players;
    Challenge tourneyChallenge;

    @BeforeClass
    public static void suiteSetUp() throws Exception {
        players = new ArrayList<TournamentPlayer>();
        for(int i = 0; i < 10; ++i){
            players.add(new TournamentPlayer(new Socket()));
        }
    }

    @Before
    public void testSetup() throws Exception {

        tourneyChallenge = new Challenge(players, 0, RoundInfo.getInstance());
    }

    @Test
    public void setMatchupType() throws Exception {
    }

    @Test
    public void playNextRound() throws Exception {
    }

    @Test
    public void getCurrentRound() throws Exception {
    }

    @Test
    public void getRoundsRemaining() throws Exception {
    }

}