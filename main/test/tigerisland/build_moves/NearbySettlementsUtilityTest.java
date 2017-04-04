package tigerisland.build_moves;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;


//public NearbySettlementsUtility(BuildActionData data, SettlementBoard board){
//        this.player = data.getPlayer();
//        this.board = board;
//        this.id = player.getId();
//        this.location = data.getHexBuildLocation();
//
//        }
//
//public List<Settlement> getPossibleSettlementsForBuild(){
//
//
//        List<Location> locations = location.getSurroundingLocations();
//        List<Settlement> settlements = new ArrayList<>();
//        for (Location location : locations){
//        boolean hasSettlement;
//        hasSettlement = board.isLocationOccupied(location, id);
////            try {
////
////            }
////            catch(Exception e){
////                continue;
////            }
//        if (hasSettlement){
//        settlements.add(board.getSettlement(location));
//        }
////            else
////                locations.remove(location);
//        }
//
//        return settlements;





public class NearbySettlementsUtilityTest {



    private NearbySettlementsUtility nearbyFinder;
    private Player player1 = new Player();
    private Player player2 = new Player();

    private SettlementBoard settlementBoard;
    private PieceBoard pieceBoard;


    @Before
    public void setUp() throws Exception {

        pieceBoard = new PieceBoardImpl();
        settlementBoard = new LazySettlementBoard(pieceBoard);

    }

    @Test
    public void test_ShouldNotReturnAnyNearbySettlements(){

        //arrange
        Location buildLoc = new Location(0,0,0);


        BuildActionData data = new BuildActionData.Builder()
                .withPlayer(player1)
                .withHexLocation(buildLoc)
                .build();

        nearbyFinder = new NearbySettlementsUtility(data, settlementBoard);

        //act
        List<Settlement> settlements = nearbyFinder.getPossibleSettlementsForBuild();

        Assert.assertTrue(settlements.size()==0);

    }
    @Test
    public void test_ShouldReturnOneNearbySettlements(){


        //arrange
        Location buildLoc = new Location(0,0,0);

        List <Location> settlementLocations = buildLoc.getSurroundingLocations();


        for (Location settlementLocation : settlementLocations)
            pieceBoard.addPiece(new Villager(),settlementLocation,player1.getId());

        settlementBoard = new LazySettlementBoard(pieceBoard);

        BuildActionData data = new BuildActionData.Builder()
                .withPlayer(player1)
                .withHexLocation(buildLoc)
                .build();

        nearbyFinder = new NearbySettlementsUtility(data, settlementBoard);

        //act
        List<Settlement> settlements = nearbyFinder.getPossibleSettlementsForBuild();

        //assert
        Assert.assertTrue(settlementLocations.containsAll(settlements.get(0).getConnectedLocations()));

        Assert.assertTrue(settlements.size()==1);



    }

    @Test
    public void test_ShouldOnlyReturnPlayerOneSettlements(){

        //arrange
        Location buildLoc = new Location(0,0,0);

        List <Location> settlementLocations = buildLoc.getSurroundingLocations();

        int i = 0;

        List <Location> playerOneLocations = new ArrayList<>();
        for (Location settlementLocation : settlementLocations) {
            if (i%2==0) {
                pieceBoard.addPiece(new Villager(), settlementLocation, player1.getId());
                playerOneLocations.add(settlementLocation);
            }
            else
                pieceBoard.addPiece(new Villager(), settlementLocation, player2.getId());
            ++i;
        }

        List<Location> playerTwoLocations =  settlementLocations;
        playerTwoLocations.removeAll(playerOneLocations);

        settlementBoard = new LazySettlementBoard(pieceBoard);

        BuildActionData data = new BuildActionData.Builder()
                .withPlayer(player1)
                .withHexLocation(buildLoc)
                .build();

        nearbyFinder = new NearbySettlementsUtility(data, settlementBoard);

        //act
        List<Settlement> settlements = nearbyFinder.getPossibleSettlementsForBuild();

        //assert
        for (Settlement settlement : settlements)
            Assert.assertTrue(playerOneLocations.containsAll(settlement.getConnectedLocations()));

        Assert.assertTrue(settlements.size()==3);

        for (Settlement settlement : settlements)
            Assert.assertFalse(playerTwoLocations.containsAll(settlement.getConnectedLocations()));
    }
}