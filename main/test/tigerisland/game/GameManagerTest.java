package tigerisland.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.player.Player;
import tigerisland.terrains.Jungle;
import tigerisland.terrains.Lake;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile.TileDeck;

import java.util.ArrayList;
import java.util.List;

public class GameManagerTest {
    private GameManager manager;

    TileDeck deck = new TileDeck(12341298);

    Player playerOne = new Player();



    @Before
    public void setUp() throws Exception {
        manager = new GameManager();
    }

    @Test
    public void test_PlaceTileShouldBeTrueNukingStartingHex() {

        //arrange
        //starting tile initialized by GameManager
        manager = new GameManager();

        Location location = new Location(0,0,0);

        Tile toPlace = deck.drawTile();
        toPlace.setOrientation(Orientation.getEast());

        //act/assert
        Assert.assertTrue(manager.placeTile(toPlace, location));

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
        manager = new GameManager();

        tile.setOrientation((Orientation.getSouthEast()));

        //act
        Assert.assertTrue(manager.placeTile(tile, location));
    }

    @Test
    public void test_ShouldReturnTrueForFoundingSettlementOnLevelOne(){

        Location location = new Location(0,-1,1);
        Assert.assertTrue(manager.foundSettlement(location, playerOne));
    }

    @Test
    public void test_ShouldReturnFalseForFoundingOnLevelGreatThanOne(){

        //arrange

        Location location = new Location(0,0,0);

        Tile toPlace = deck.drawTile();
        toPlace.setOrientation(Orientation.getEast());
        manager.placeTile(toPlace,location);

        Location settleLocation = new Location(0,-1,1);

        //assert

        Assert.assertFalse(manager.foundSettlement(settleLocation,playerOne));
    }

    @Test
    public void test_ShouldReturnFalseForFoundingOnVolcanoHex() {
        //arrange

        Location location = new Location(0,0,0);

        Assert.assertFalse(manager.foundSettlement(location,playerOne));
    }

    @Test
    public void test_expandSettlementShouldReturnTrueForValidSettlementExpansion() {
        //arrange
        Location found = new Location(0,0,0).getAdjacent(Orientation.getNorthEast());

        manager.foundSettlement(found, playerOne);

        Location expandTo = found.getAdjacent(Orientation.getWest());



        Assert.assertTrue(manager.expandSettlement(found, Jungle.getInstance(), playerOne));
    }

//    @Test
//
//    public void test_expandSettlementShouldReturnFalseForInvalidSettlementExpansion() {
//        //arrange
//        Location found = new Location(0,0,0).getAdjacent(Orientation.getNorthEast());
//
//        Assert.assertTrue(manager.foundSettlement(found, playerOne));
//
//        Location expandTo = found.getAdjacent(Orientation.getWest());
//
//
//
//        Assert.assertFalse(manager.expandSettlement(found, Rocky.getInstance(), playerOne));
//
//        //Todo
//    }

    @Test

    public void test_BuildTotoroShouldReturnTrueForValidTotoroBuild(){



        List<Tile> tiles = new ArrayList();

        for (int i = 12; i<15; ++i){
            Hex hex = new Hex(Lake.getInstance());
            tiles.add(new Tile(i,hex,hex));
        }


        Tile toPlace = tiles.get(0);
        toPlace.setOrientation(Orientation.getSouthWest());
        Assert.assertTrue(manager.placeTile(toPlace, new Location(2,-2,0)));

        toPlace = tiles.get(1);
        toPlace.setOrientation(Orientation.getWest());

        Assert.assertTrue(manager.placeTile(toPlace, new Location(3,-3,0) ));

        Location settlement = new Location(2,-1,-1);

        Assert.assertTrue(manager.foundSettlement(settlement, playerOne));
        Assert.assertTrue(manager.expandSettlement(settlement, Lake.getInstance(),playerOne));

        Assert.assertTrue(manager.buildTotoro(new Location(0,1,-1), playerOne));

    }



    private void createTotoroSettlement(){






    }



//    private Location createSettlement(Location startLocation){
//        manager = new GameManager();
//        Location next = startLocation;
//
//        List<Orientation> orientations = new ArrayList<>();
//
//        orientations.add(Orientation.getEast());
//        orientations.add(Orientation.getNorthEast());
//        orientations.add(Orientation.getNorthWest());
//        orientations.add(Orientation.getWest());
//        orientations.add(Orientation.getSouthWest());
//
//
//        for (Orientation orientation : orientations){
//            Tile tile = deck.drawTile();
//            tile.setOrientation(orientation);
//
//
//        }
//
//
//
//        for (int i = 0; i<5; ++i){
//            Tile tile = deck.drawTile();
//            tile.setOrientation(Orientation.getSouthWest());
//            Assert.assertTrue(manager.placeTile(tile,next));
//            Assert.assertTrue(manager.foundSettlement(next.getAdjacent(Orientation.getEast()), playerOne));
//            manager.foundSettlement(next.getAdjacent(Orientation.getEast()), playerOne);
//            next = next.getAdjacent(Orientation.getEast()).getAdjacent(Orientation.getEast());
//        }
//        return next;
//    }




    @Test
    public void endGame() throws Exception {

    }

    @Test
    public void returnResults() throws Exception {

    }

}