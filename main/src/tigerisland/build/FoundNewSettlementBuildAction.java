package tigerisland.build;

import tigerisland.board.Board;
import tigerisland.build.actions.MakeBuildAction;
import tigerisland.build.actions.PlaceVillagerOnHexAction;
import tigerisland.build.rules.*;
import tigerisland.piece.PieceBoard;

import java.util.ArrayList;
import java.util.List;

public class FoundNewSettlementBuildAction extends BuildAction{

    private final String BUILD_ACTION_NAME = "Found New Settlement";
    private Board board;
    private PieceBoard pieceBoard;

    public FoundNewSettlementBuildAction(Board board, PieceBoard pieceBoard) {
       this.board = board;
       this.pieceBoard = pieceBoard;
       this.rules = createBuildActionRules();
       this.actions = createBuildActions();
    }

    @Override
    public List<BuildActionRule> createBuildActionRules() {
        List<BuildActionRule> rules = new ArrayList<>();
        rules.add( new HexOnBoardRule(board));
        rules.add( new EmptyHexRule(pieceBoard));
        rules.add( new HexLevelOneRule(board));
        rules.add( new NoVolcanoForBuildLocationRule(board));
        rules.add( new NotEnoughVillagersRule());
        return rules;
    }

    @Override
    public List<MakeBuildAction> createBuildActions() {
        List<MakeBuildAction> actions = new ArrayList<>();
        actions.add( new PlaceVillagerOnHexAction(pieceBoard));
        return actions;
    }

    @Override
    public String getBuildActionName() {
        return this.BUILD_ACTION_NAME;
    }
}
