package tigerisland.settlement;

import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.piece.*;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;



public class LazySettlementBoardTest {
    class TestPieceBoardNoPlayers implements PieceBoard {
        @Override
        public PlayerID getPlayer(Location location) {
            return CreatePlayerID.createPlayerID();
        }

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

    Location p1Loc = null;
    Location p2Loc = null;
    SettlementBoard settlementBoard = null;
    TestPieceBoardNoPlayers pieceBoard = null;

    private void setUpTwoPlayerBoard() {
        p1Loc = new Location(0,0,0);
        p2Loc = p1Loc.getAdjacent(Orientation.getEast());
        pieceBoard = new TestPieceBoardNoPlayers() {
            @Override
            public PlayerID getPlayer(Location location) {
                if (p1Loc.equals(location)) {
                    return CreatePlayerID.getP1();
                } else if (p2Loc.equals(location)) {
                    return CreatePlayerID.getP2();
                } else {
                    return null;

                }
            }
        };
        settlementBoard = new LazySettlementBoard(pieceBoard);
    }


    public void setUpNoPlayerBoard() throws Exception {
        pieceBoard = new TestPieceBoardNoPlayers();
        settlementBoard = new LazySettlementBoard(pieceBoard);

    }
    @Test
    public void GivenUnoccupiedLocationThenAttemptingtoGetSettlementThenException() throws Exception {
        setUpNoPlayerBoard();
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
        setUpNoPlayerBoard();
        Location location = new Location(0,0,0);
        assertFalse(settlementBoard.LocationOccupiedp(location));
    }

    @Test
    public void GivenOccupiedLocationThenAttemptingtoGetSettlementThenGetSettlement() throws Exception {
        setUpNoPlayerBoard();
        Location location = new Location(0,0,0);
        addAnythingToPieceBoard(location);
        Settlement s = settlementBoard.getSettlement(location);
        assertNotNull(s);
        assertEquals(s.settlementSize(),1);
    }

    @Test
    public void GivenOccupiedLocationThenQueryingIfOccupiedThenTrue() throws Exception {
        setUpNoPlayerBoard();
        Location location = new Location(0,0,0);
        addAnythingToPieceBoard(location);
        assertTrue(settlementBoard.LocationOccupiedp(location));
    }



    @Test
    public void GivenLargerSettlementThenQuerySizeThenCorrectSize() throws Exception {
        setUpNoPlayerBoard();
        Location locations[] = getSquareOfLocations();
        for ( Location loc : locations ) {
            addAnythingToPieceBoard(loc);
        }
        for ( Location loc : locations ) {
            assertEquals(settlementBoard.getSettlement(loc).settlementSize(),locations.length);
        }
    }


    @Test
    public void GivenLargerSettlementThenQuerySizeThenCorrectPieces() throws Exception {
        setUpNoPlayerBoard();
        Location locations[] = getSquareOfLocations();
        boolean step = false;
        for ( Location loc : locations ) {
            if ( step ) {
                addTotorotToPieceBoard(loc);
            } else {
                addVillagerToPieceBoard(loc);
            }
        }
        step = false;
        for ( Location base_loc : locations ) {
            Settlement settlement = settlementBoard.getSettlement(base_loc);
            for (Location loc : locations) {
                Piece p = settlement.getPieceAt(loc);
                if (step) {
                    p.accept(new PieceVisitor() {
                        @Override
                        public void visitVillager(Villager villager) {
                            assert (false);
                        }

                        @Override
                        public void visitTotoro(Totoro totoro) {
                            assert (true);
                        }
                    });
                } else {
                    p.accept(new PieceVisitor() {
                        @Override
                        public void visitVillager(Villager villager) {
                            assert (true);
                        }

                        @Override
                        public void visitTotoro(Totoro totoro) {
                            assert (false);
                        }
                    });
                }
            }
        }
    }

    private Location[] getSquareOfLocations() {
        Location center = new Location(0,0);
        List<Location> list = center.getSurroundingLocations();
        list.add(center);
        assert(list.size() > 1);
        return list.toArray(new Location[list.size()]);
    }

    @Test
    public void TestDifferentPlayersStaySeparate() throws Exception {
        setUpTwoPlayerBoard();
        addAnythingToPieceBoard(p1Loc);
        addAnythingToPieceBoard(p2Loc);
        Settlement p1 = settlementBoard.getSettlement(p1Loc);
        Settlement p2 = settlementBoard.getSettlement(p2Loc);
        assertEquals(1,p1.settlementSize());
        assertEquals(1,p2.settlementSize());
        assert(p1.LocationOccupiedp(p1Loc));
        assert(p2.LocationOccupiedp(p2Loc));
    }


    private void addAnythingToPieceBoard(Location l) {
        addPieceToPieceBoard(l, new Villager());
    }
    private void addVillagerToPieceBoard(Location l) {
       addPieceToPieceBoard(l,new Villager());
    }

    private void addTotorotToPieceBoard(Location l) {
        addPieceToPieceBoard(l,new Totoro());
    }

    private void addPieceToPieceBoard(Location l, Piece p) {
        pieceBoard.addPiece(l,p);
    }
}