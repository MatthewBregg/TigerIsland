package tigerislandserver.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tigerisland.game.GameManager;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;
import tigerislandserver.gameplay.GameThread;
import tigerislandserver.gameplay.TournamentScoreboard;

import java.net.Socket;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class GameInputAdapterTest {

    @Before
    public void setUp() throws Exception {


    }

    @Test

    public void test_MakeMove(){

        InputAdapterDebugObject debug = new InputAdapterDebugObject();

        String clientMove = "GAME A MOVE 0 PLACE ROCK+LAKE AT 0 -2 2 4 FOUND SETTLEMENT AT -1 -2 3";


        Socket socket1 = mock(Socket.class);

        TournamentPlayerMock playerOne = new TournamentPlayerMock(socket1);
        TournamentPlayerMock playerTwo = new TournamentPlayerMock(new Socket());

        ArrayList<Tile> tiles = generateTileArray();

        TournamentScoreboard scoreBoard = new TournamentScoreboard();


        GameThread thread = new GameThread(playerOne, playerTwo, tiles, 'A', 0 ,scoreBoard, 0);

        Tile tile = new Tile(4, Lake.getInstance(),Rocky.getInstance());

        GameInputAdapterInjected.makeMove(thread, playerOne, clientMove, 'A', 0, tile, debug);

        GameManager manager = debug.getInitialGM();

//        Assert.assert(manager.)


    }



    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void makeMove() throws Exception {

    }
    private ArrayList<Tile> generateTileArray() {

        TileDeck tileDeck = new TileDeck(12340812);

        ArrayList<Tile> tiles = new ArrayList<>();
        int totalTiles = tileDeck.getCount();
        for(int i = 0; i < totalTiles; ++i){
            Tile t = tileDeck.drawTile();
            tiles.add(t);
        }

        return tiles;
    }
}