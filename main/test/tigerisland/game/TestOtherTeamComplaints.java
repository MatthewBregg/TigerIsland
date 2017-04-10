package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.TestLogger;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.piece.PieceBoard;
import tigerisland.player.Player;
import tigerisland.test_boards.TestingBoardMaker;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;

import java.util.ArrayList;

public class TestOtherTeamComplaints {
    private GameManager manager;
    private ArrayList<Player> players;
    private TileDeck deck;

    private Player playerOne;

    private TestingBoardMaker debugBoard;


    @Before
    public void setUp() throws Exception {
        deck = new TileDeck(12341298);
        this.players = new ArrayList<Player>();
        playerOne = new Player();
        players.add(playerOne);
        players.add(new Player());

        manager = new GameManager(players, new TestLogger());
    }

    @Test
    public void test_ValidFirstMove(){

        //I dont know the team name but due in glasses with headphones

        debugBoard = new TestingBoardMaker("/resources/TileFirstMoveTest.txt", players);
        HexBoard hexBoard = debugBoard.getBoard();
        PieceBoard pieces = debugBoard.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(hexBoard,players,pieces, new TestLogger());

        Tile tile = new Tile();
        Location location = new Location (0,-2,2);
        tile.setOrientation(Orientation.getEast());


        //tile hex locaiton terrain does not matter
//        -1 -2 3 1 1 lake null 0
//        0 -2 2 1 1 volcano null 1
//        0 -3 3 1 1 lake null 1

        Assert.assertTrue(manager.placeTile(tile,location));

        for (int i = 0; i<7; ++i){
            tile.rotate();
            Assert.assertFalse(manager.placeTile(tile, location));
        }
        Location settlement = new Location (-1 , -2, 3);

        Assert.assertTrue(manager.foundSettlement(settlement, playerOne));

    }


    @Test
    public void test_PlaceTileInvalidSameTile(){

        //Dark haired dude who was first to connect in testing yesterday?

        debugBoard = new TestingBoardMaker("/resources/TilePlaceOnSameTile.txt", players);
        HexBoard hexBoard = debugBoard.getBoard();
        PieceBoard pieces = debugBoard.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(hexBoard,players,pieces, new TestLogger());
        Tile tile = new Tile();
        Location location = new Location (2,-2,0);
        tile.setOrientation(Orientation.getSouthWest());


        Assert.assertFalse(manager.placeTile(tile,location));



        for (int i = 0; i<7; ++i){
            tile.rotate();
            Assert.assertFalse(manager.placeTile(tile, location));
        }

    }



}
