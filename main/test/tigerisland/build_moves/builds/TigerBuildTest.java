package tigerisland.build_moves.builds;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

public class TigerBuildTest {

    private Board board;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private ScoreManager scoreManager;
    TigerBuild build;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_ShouldReturnAppropriateBuildRules() throws Exception {

        //arrange
        build = new TigerBuild(board, pieceBoard,settlementBoard,scoreManager);



    }

    @Test
    public void createBuildActions() throws Exception {

    }

}