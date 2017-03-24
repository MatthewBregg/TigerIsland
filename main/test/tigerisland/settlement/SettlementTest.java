package tigerisland.settlement;

import org.junit.Assert;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.piece.*;

import java.util.*;

import static org.junit.Assert.*;

public class SettlementTest {
    Settlement settlement = null;
    Location locations[] = null;

    private void createSettlement() {
        Map<Location,Piece> pieceMap = new HashMap<Location,Piece>();
        for ( Location loc : locations ) {
            pieceMap.put(loc,null);
        }
        settlement = new Settlement(pieceMap, CreatePlayerID.getPlayerID());
    }
    @Test
    public void GivenEmptySettlementWhenQuerySizeThenGet0() throws Exception {
        locations = new Location[]{};
        createSettlement();
        assertEquals(settlement.settlementSize(), 0);
    }

    @Test
    public void GivenNonEmptySettlementWhenQuerySizeThenGetSize() throws Exception {
        locations = new Location[]{new Location(0,0), new Location(1,1)};
        createSettlement();
        assertEquals(settlement.settlementSize(), 2);
    }

    @Test
    public void getConnectedLocations() throws Exception {
        locations = new Location[]{new Location(0,0), new Location(1,1)};
        createSettlement();

        List<Location> locList = new ArrayList<Location>(Arrays.asList(locations));
        assertTrue(locList.containsAll(settlement.getConnectedLocations()));
        assertTrue(settlement.getConnectedLocations().containsAll(locList));

    }

    @Test
    public void getPieceAt() throws Exception {
        Map<Location,Piece> pieceMap = new HashMap<Location,Piece>();
        Totoro t = new Totoro();
        Villager v = new Villager();
        pieceMap.put(new Location(0,0),t);
        pieceMap.put(new Location(0,1),v);
        settlement = new Settlement(pieceMap,CreatePlayerID.getPlayerID());
        Boolean[] checkedPiece = new Boolean[]{false};
        settlement.acceptVisitor(new PieceVisitor() {
           @Override
           public void visitTotoro(Totoro totoro) {
               assertEquals(totoro,t);
               checkedPiece[0] = true;
           }
        });
        Assert.assertTrue(checkedPiece[0]);
        checkedPiece[0] = false;
        settlement.acceptVisitor(new PieceVisitor() {
           @Override
           public void visitVillager(Villager villager) {
               assertEquals(villager,v);
               checkedPiece[0] = true;
           }
        });
        Assert.assertTrue(checkedPiece[0]);
        checkedPiece[0] = false;
    }


    @Test
    public void locationOccupiedp() throws Exception {
        locations = new Location[]{new Location(0,0)};
        createSettlement();
        assertTrue(settlement.LocationOccupiedp(new Location(0,0)));
        assertFalse(settlement.LocationOccupiedp(new Location(0,1)));
    }

}