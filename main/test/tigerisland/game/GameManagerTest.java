package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.TestLogger;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.piece.PieceBoard;
import tigerisland.player.Player;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Lake;
import tigerisland.terrains.Rocky;
import tigerisland.test_boards.TestingBoardMaker;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;

import java.util.ArrayList;
import java.util.List;

//@Ignore("Fix the path GameManagerTigerFailTestBoard.txt . It is machine specific")
public class GameManagerTest {
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
    public void test_PlaceTileShouldBeTruePlacingNextToStartingHex() {
        //arrange

        Tile tile = deck.drawTile();
        Location location = new Location(-1,1,0);
        tile.setOrientation(Orientation.getSouthWest());

        //act/assert
        Assert.assertTrue(manager.placeTile(tile, location));

        //test with other possible orientation
        //arrange
        manager= new GameManager(players, new TestLogger());

        tile.setOrientation((Orientation.getSouthEast()));

        //act
        Assert.assertTrue(manager.placeTile(tile, location));
    }

    @Test
    public void test_PlaceTileShouldReturnFalsePlacingOnNonAdjacentLocation(){
        //arrange

        Tile tile = deck.drawTile();
        Location location = new Location(-5,5,0);
        tile.setOrientation(Orientation.getSouthWest());

        //act/assert
        Assert.assertFalse(manager.placeTile(tile, location));

    }

    @Test
    public void test_PlaceTileShouldReturnFalsePlacingTileOnHexesOfDifferentLevels() throws InvalidTilePlacementException{
        //arrange

        Tile tile = deck.drawTile();
        Location location = new Location(1,-1,0);
        tile.setOrientation(Orientation.getSouthWest());

        //act/assert
        Assert.assertFalse(manager.placeTile(tile, location));

    }

    @Test
    public void test_FoundSettlementShouldReturnTrueForFoundingSettlementOnLevelOne(){

        Location location = new Location(0,-1,1);
        Assert.assertTrue(manager.foundSettlement(location, playerOne));
    }

    @Test
    public void test_FoundSettlementShouldReturnFalseForFoundingOnLevelGreaterThanOne(){
        //tiger test board has tiles all at level 3
        debugBoard = new TestingBoardMaker("/resources/GameManagerTigerPassTestBoard.txt", players);
        HexBoard hexBoard = debugBoard.getBoard();
        PieceBoard pieces = debugBoard.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(hexBoard,players,pieces, new TestLogger());
        Location settlement = new Location(1,0 -1);
        Assert.assertFalse(manager.foundSettlement(settlement, playerOne));
    }

    @Test
    public void test_FoundSettlementShouldReturnFalseForFoundingOnVolcanoHex() {

        Location location = new Location(0,0,0);

        Assert.assertFalse(manager.foundSettlement(location,playerOne));
    }

    @Test
    public void test_ExpandSettlementShouldReturnTrueForValidSettlementExpansionTerrain() {

        Location found = new Location(0,0,0).getAdjacent(Orientation.getNorthEast());

        manager.foundSettlement(found, playerOne);

        Assert.assertTrue(manager.expandSettlement(found, Jungle.getInstance(), playerOne));
    }

    @Test
    public void test_ExpandSettlementShouldReturnFalseForInvalidSettlementExpansionTerrain() {

        Location found = new Location(0,0,0).getAdjacent(Orientation.getNorthEast());

        Assert.assertTrue(manager.foundSettlement(found, playerOne));

        Assert.assertFalse(manager.expandSettlement(found, Rocky.getInstance(), playerOne));

    }

    @Test
    public void test_BuildTotoroShouldReturnTrueForValidTotoroBuild(){

        createTestBoardForTotoro();
        Location settlement = new Location(2,-1,-1);

        Assert.assertTrue(manager.foundSettlement(settlement, playerOne));
        //expanding yields settlement size 5
        Assert.assertTrue(manager.expandSettlement(settlement, Lake.getInstance(),playerOne));



        Assert.assertTrue(manager.buildTotoro(new Location(0,1,-1), playerOne));

    }

    private void createTestBoardForTotoro() {
        List<Tile> tiles = new ArrayList();

        //I used 12-15 to have a high tile id so as not to have possibility of duplication with the starting tile ids
        for (int i = 12; i<15; ++i){
            tiles.add(new Tile(i,Lake.getInstance(), Lake.getInstance()));
        }


        Tile toPlace = tiles.get(0);
        toPlace.setOrientation(Orientation.getSouthWest());
        Assert.assertTrue(manager.placeTile(toPlace, new Location(2,-2,0)));

        toPlace = tiles.get(1);
        toPlace.setOrientation(Orientation.getWest());

        Assert.assertTrue(manager.placeTile(toPlace, new Location(3,-3,0) ));

        Location settlement = new Location(2,-1,-1);
    }

    @Test
    public void test_BuildTotoroShouldReturnFalseForInvalidTotoroBuild(){
        createTestBoardForTotoro();
        Location settlement = new Location(2,-1,-1);
        Assert.assertTrue(manager.foundSettlement(settlement, playerOne));;
        //no expansion;settlement size 1; invalid totoro build

        Assert.assertFalse(manager.buildTotoro(new Location(0,1,-1), playerOne));

    }

    @Test
    public void test_BuildTigerShouldReturnTrueForValidTigerBuild(){
        createTestBoardForTiger();
        Location tiger = new Location(2,-1,-1);
        Assert.assertTrue(manager.buildTiger(tiger, playerOne));

    }

    private void createTestBoardForTiger() {
        debugBoard = new TestingBoardMaker("/resources/GameManagerTigerPassTestBoard.txt", players);
        HexBoard hexBoard = debugBoard.getBoard();
        PieceBoard pieces = debugBoard.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(hexBoard,players,pieces, new TestLogger());
    }

    @Test
    public void test_BuildTigerShouldReturnFalseForInvalidTigerBuild(){
        createInvalidTestBoardForTiger();
        Location tiger = new Location(2,-1,-1);
        Assert.assertFalse(manager.buildTiger(tiger, playerOne));
    }

    private void createInvalidTestBoardForTiger() {
        debugBoard = new TestingBoardMaker("/resources/GameManagerTigerFailTestBoard.txt", players);
        HexBoard hexBoard = debugBoard.getBoard();
        PieceBoard pieces = debugBoard.getPieces();
        manager = GameManager.injectStuffOnlyForTesting(hexBoard,players,pieces, new TestLogger());
    }

    @Test
    public void endGame() throws Exception {
        //TODO
    }

    @Test
    public void returnResults() throws Exception {
        //TODO

    }
}