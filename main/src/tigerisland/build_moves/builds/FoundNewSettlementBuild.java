package tigerisland.build_moves.builds;

import tigerisland.board.Board;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceVillagerOnHexAction;
import tigerisland.build_moves.actions.ScoreVillagersOnHex;
import tigerisland.build_moves.rules.*;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;

import java.util.ArrayList;
import java.util.List;

public class FoundNewSettlementBuild extends BuildAction{

    private ScoreManager scoreManager;
    private Board board;
    private PieceBoard pieceBoard;

    public FoundNewSettlementBuild(Board board, PieceBoard pieceBoard, ScoreManager scoreManager) {
       this.board = board;
       this.pieceBoard = pieceBoard;
       this.scoreManager = scoreManager;
    }

    @Override
    protected List<BuildActionRule> createBuildActionRules() {
        List<BuildActionRule> rules = new ArrayList<>();

        rules.add( new BuildLocationMustBeOnBoardRule(board));
        rules.add( new EmptyHexRule(pieceBoard));
        rules.add( new SettlementMustBeFoundedHexLevelOneRule(board));
        rules.add( new CannotBuildOnVolcanoRule(board));
        rules.add( new PlayerMustHaveAVillagerToBuildRule());

        return rules;
    }

    @Override
    protected List<MakeBuildAction> createBuildActions() {
        List<MakeBuildAction> actions = new ArrayList<>();

        actions.add( new PlaceVillagerOnHexAction(pieceBoard));
        actions.add( new ScoreVillagersOnHex(board, scoreManager));

        return actions;
    }

}
