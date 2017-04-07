package tigerisland.build_moves.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Jungle;
import tigerisland.tile.Orientation;

import java.util.HashSet;
import java.util.Set;

public class ScoreSettlementExpansionTest {
        private HexBoard hexBoard;
        private PieceBoard pieceBoard;
        private SettlementBoard settlementBoard;
        private SettlementExpansionUtility settlementExpansionUtility;
        private ScoreSettlementExpansion scoreSettlementExpansion;
        private ScoreManager scoreManager;

        @Before
        public void setUp() throws Exception {
            hexBoard = new HexBoard();
            pieceBoard = new PieceBoardImpl();
            scoreManager = new ScoreManager();
            settlementBoard = new LazySettlementBoard(pieceBoard);
            settlementExpansionUtility = new SettlementExpansionUtility(hexBoard,pieceBoard,settlementBoard);
            scoreSettlementExpansion = new ScoreSettlementExpansion(scoreManager,settlementExpansionUtility);
        }

        Location firstLoc = null;
        Set<Location> locations_to_place = null;
        private void PrimeBoard() {
            locations_to_place = null;
            locations_to_place = new HashSet<Location>();
            Location primingLoc = new Location(0,0,0);
            firstLoc = primingLoc.getAdjacent(Orientation.getEast());
            for ( int i = 0; i != 3; ++i ) {
                primingLoc = primingLoc.getAdjacent(Orientation.getEast());
                locations_to_place.add(primingLoc);
            }
            int i = 1;
            for ( Location loc : locations_to_place ) {
                Hex h = new Hex(Jungle.getInstance());
                h.setLevel(i);
                ++i;
                hexBoard.placeHex(loc,h);
            }
        }


        private void runTestWithPlayer(Player p) {
            PrimeBoard();
            BuildActionData.Builder builder = new BuildActionData.Builder();
            builder.withTerrain(hexBoard.getHex(firstLoc).getTerrain());
            Location settlementLocA = firstLoc.getAdjacent(Orientation.getEast());
            builder.withSettlementLocation(settlementLocA);
            pieceBoard.addPiece(new Villager(), settlementLocA, p.getId());
            builder.withPlayer(p);
            scoreSettlementExpansion.applyAction(builder.build());
        }

        @Test
        public void test_BuildingASettlement() throws Exception {
            Player player = new Player();
            int villagerCountInit = player.getVillagerCount();
            runTestWithPlayer(player);
            Assert.assertEquals(10,scoreManager.getPlayerScore(player.getId()));
        }
}