package tigerisland.build_moves.builds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceVillagerOnHexAction;
import tigerisland.build_moves.rules.*;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.score.ScoreManager;

import java.util.List;

public class FoundNewSettlementBuildTest {

    private FoundNewSettlementBuild foundNewSettlementBuildAction;
    private Board board;
    private PieceBoard pieceBoard;
    private ScoreManager scoreManager;

    @Before
    public void setup() {
        this.board = new HexBoard();
        this.pieceBoard = new PieceBoardImpl();
        this.scoreManager = new ScoreManager();
        foundNewSettlementBuildAction = new FoundNewSettlementBuild(board, pieceBoard, scoreManager);
    }

    @Test
    public void test_ShouldFindHexOnBoardRuleWhenBuildActionIsCreated() {

        // Arrange

        // Act
        List<BuildActionRule> rules =  foundNewSettlementBuildAction.createBuildActionRules();

        // Assert
        BuildActionRule hexOnBoardRule = null;

        for(BuildActionRule  rule : rules) {
            if (rule instanceof BuildLocationMustBeOnBoardRule)
                hexOnBoardRule = rule;
        }

        Assert.assertNotNull(hexOnBoardRule);
    }


    @Test
    public void test_ShouldFindEmptyHexRuleWhenBuildActionIsCreated() {

        // Arrange

        // Act
        List<BuildActionRule> rules =  foundNewSettlementBuildAction.createBuildActionRules();

        // Assert
        BuildActionRule emptyHexRule = null;

        for(BuildActionRule  rule : rules) {
            if (rule instanceof EmptyHexRule)
                emptyHexRule = rule;
        }

        Assert.assertNotNull(emptyHexRule);
    }

    @Test
    public void test_ShouldFindHexLevelOneRuleWhenBuildActionIsCreated() {

        // Arrange

        // Act
        List<BuildActionRule> rules =  foundNewSettlementBuildAction.createBuildActionRules();

        // Assert
        BuildActionRule hexLevelOneRule = null;

        for(BuildActionRule  rule : rules) {
            if (rule instanceof SettlementMustBeFoundedHexLevelOneRule)
                hexLevelOneRule = rule;
        }

        Assert.assertNotNull(hexLevelOneRule);
    }

    @Test
    public void test_ShouldFindNoVolcanoOnBuildLocationRuleWhenBuildActionIsCreated() {

        // Arrange

        // Act
        List<BuildActionRule> rules =  foundNewSettlementBuildAction.createBuildActionRules();

        // Assert
        BuildActionRule noVolcanoOnBuildLocationRule = null;

        for(BuildActionRule  rule : rules) {
            if (rule instanceof CannotBuildOnVolcanoRule)
                noVolcanoOnBuildLocationRule = rule;
        }

        Assert.assertNotNull(noVolcanoOnBuildLocationRule);
    }

    @Test
    public void test_ShouldFindNotEnoughVillagersRuleWhenBuildActionIsCreated() {

        // Arrange

        // Act
        List<BuildActionRule> rules =  foundNewSettlementBuildAction.createBuildActionRules();

        // Assert
        BuildActionRule noVolcanoOnBuildLocationRule = null;

        for(BuildActionRule  rule : rules) {
            if (rule instanceof CannotBuildOnVolcanoRule)
                noVolcanoOnBuildLocationRule = rule;
        }

        Assert.assertNotNull(noVolcanoOnBuildLocationRule);
    }

    @Test
    public void test_ShouldFindPlaceOnHexVillagerAction() {

        // Arrange

        // Act
        List<MakeBuildAction> actions =  foundNewSettlementBuildAction.createBuildActions();

        // Assert
        MakeBuildAction placeVillagerOnHex = null;

        for(MakeBuildAction action: actions) {
            if (action instanceof PlaceVillagerOnHexAction)
                placeVillagerOnHex = action ;
        }

        Assert.assertNotNull(placeVillagerOnHex);
    }



}
