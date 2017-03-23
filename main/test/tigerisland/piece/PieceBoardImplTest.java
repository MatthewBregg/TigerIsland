package tigerisland.piece;

import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.player.PlayerID;

import static org.junit.Assert.*;

public class PieceBoardImplTest {
    PieceBoardImpl pc = new PieceBoardImpl();
    PlayerID pID = new PlayerID();
    Location locationZero = new Location(0,0,0);

    private Piece addAnyNonNullPiece(Location loc,PlayerID pID) {
        Piece p = new Villager();
        pc.addPiece(p,loc,pID);
        return p;
    }
    @Test
    public void test_WhenUnOccupiedThengetNullPiece() throws Exception {
        Piece p = pc.getPiece(locationZero);
        assertNotNull(p);
        assert(p instanceof NullPiece);
    }

    @Test
    public void test_WhenUnOccupiedThenLocationOccupiedpReturnsFalse() throws Exception {
        assertFalse(pc.LocationOccupiedp(locationZero));
        assertFalse(pc.LocationOccupiedp(locationZero,pID));
    }

    @Test
    public void test_whenUnOccupiedThenGetNullForPlayer() throws Exception {
        assertNull(pc.getPlayer(locationZero));
    }

    @Test
    public void test_AddingToBoard() throws Exception {
        pc.addPiece(new Villager(), new Location(0,0,0), pID);
    }

    @Test
    public void test_WhenOccupiedThengetPiece() throws Exception {
        Piece p = addAnyNonNullPiece(locationZero,pID);
        assertEquals(p,(pc.getPiece(locationZero)));
        assertEquals(p,pc.getPiece(locationZero,pID));
    }

    @Test
    public void test_WhenOccupiedThengetPlayer() throws Exception {
        Piece p = addAnyNonNullPiece(locationZero,pID);
        assertEquals(pID,pc.getPlayer(locationZero));

    }

    @Test
    public void test_WhenOccupiedThenLocationOccupiedpReturnsTrue() throws Exception {
        Piece p = addAnyNonNullPiece(locationZero,pID);
        assertTrue(pc.LocationOccupiedp(locationZero));
        assertTrue(pc.LocationOccupiedp(locationZero,pID));
    }

    @Test
    public void test_WhenOccupiedThenClearNullPieceReturned() throws Exception {
        occupyThenClear();
        assertTrue(pc.getPiece(locationZero) instanceof NullPiece);
        assertTrue(pc.getPiece(locationZero,pID) instanceof NullPiece);
    }

    @Test
    public void test_WhenOccupiedThenClearThenLocationIsFalse() throws Exception {
        occupyThenClear();
        assertFalse(pc.LocationOccupiedp(locationZero));
        assertFalse(pc.LocationOccupiedp(locationZero,pID));
    }

    @Test
    public void test_WhenOccupiedThenClearThenPlayerIsNull() throws Exception {
        occupyThenClear();
        assertNotEquals(pc.getPlayer(locationZero),pID);
    }

    @Test
    public void test_WhenOccupiedThenClearThenPriorPieceIsNotReturned() throws Exception {
        Piece p = occupyThenClear();
        assertNotEquals(p,(pc.getPiece(locationZero)));
        assertNotEquals(p,pc.getPiece(locationZero,pID));
    }

    private Piece occupyThenClear() {
        Piece p = addAnyNonNullPiece(locationZero,pID);
        pc.clearLocation(locationZero);
        return p;
    }



}