package tigerisland.settlement;

import com.intellij.vcs.log.Hash;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.piece.Totoro;
import tigerisland.piece.Villager;

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
        settlement = new Settlement(pieceMap, CreatePlayerID.createPlayerID());
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
        settlement = new Settlement(pieceMap,CreatePlayerID.createPlayerID());
        assertEquals(settlement.getPieceAt(new Location(0,0)),t);
        assertEquals(settlement.getPieceAt(new Location(0,1)),v);
    }

    @Test
    public void locationOccupiedp() throws Exception {
        locations = new Location[]{new Location(0,0)};
        createSettlement();
        assertTrue(settlement.LocationOccupiedp(new Location(0,0)));
        assertFalse(settlement.LocationOccupiedp(new Location(0,1)));
    }

}