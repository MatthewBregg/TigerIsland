package tigerisland.settlement;

import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.piece.Piece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.Villager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;



public class LazySettlementBoardTest {
    class TestPieceBoard implements PieceBoard {
        Map<Location, Piece> pieceMap = new HashMap<Location,Piece>();
        @Override
        public Piece getPiece(Location location) {
            return pieceMap.get(location);
        }

        @Override
        public boolean LocationOccupiedp(Location location) {
            return pieceMap.containsKey(location);
        }

        public void addPiece(Location location, Piece piece) {
            pieceMap.put(location, piece);

        }
    }
    SettlementBoard settlementBoard = null;
    TestPieceBoard pieceBoard = null;

    @Before
    public void setUp() throws Exception {
        pieceBoard = new TestPieceBoard();
        settlementBoard = new LazySettlementBoard(pieceBoard);

    }
    @Test
    public void GivenUnoccupiedLocationThenAttemptingtoGetSettlementThenException() throws Exception {
        Location location = new Location(0,0,0);
        boolean thrown = false;
        try {
            settlementBoard.getSettlement(location);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assert(thrown);
    }

    @Test
    public void GivenUnoccupiedLocationThenQueryingIfOccupiedThenFalse() throws Exception {
        Location location = new Location(0,0,0);
        assertFalse(settlementBoard.LocationOccupiedp(location));
    }

    @Test
    public void GivenOccupiedLocationThenAttemptingtoGetSettlementThenGetSettlement() throws Exception {
        Location location = new Location(0,0,0);
        pieceBoard.addPiece(location, new Villager());
        Settlement s = settlementBoard.getSettlement(location);
        assertNotNull(s);
        assertEquals(s.settlementSize(),1);
    }

    @Test
    public void GivenOccupiedLocationThenQueryingIfOccupiedThenTrue() throws Exception {
        Location location = new Location(0,0,0);
        pieceBoard.addPiece(location, new Villager());
        assertTrue(settlementBoard.LocationOccupiedp(location));
    }

}