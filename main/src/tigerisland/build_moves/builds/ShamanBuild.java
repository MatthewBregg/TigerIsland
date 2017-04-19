package tigerisland.build_moves.builds;

import tigerisland.board.Board;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceShamanOnHexAction;
import tigerisland.build_moves.actions.PlaceTigerOnHexAction;
import tigerisland.build_moves.actions.ScoreShamanOnHex;
import tigerisland.build_moves.rules.*;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;

public class ShamanBuild extends BuildAction {
    private Board board;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private ScoreManager scoreManager;

    public ShamanBuild(Board board, PieceBoard pieceBoard, SettlementBoard settlementBoard, ScoreManager scoreManager) {
        this.board = board;
        this.pieceBoard = pieceBoard;
        this.settlementBoard = settlementBoard;
        this.scoreManager = scoreManager;
    }

    @Override
    protected List<BuildActionRule> createBuildActionRules() {
        List<BuildActionRule> rules = new ArrayList<>();
        rules.add( new PlayerMustHaveAShamanToBuildRule());
        rules.add( new BuildLocationMustBeOnBoardRule(board));
        rules.add( new EmptyHexRule(pieceBoard));
        rules.add( new CannotBuildOnVolcanoRule(board));
        return rules;
    }

    @Override
    protected List<MakeBuildAction> createBuildActions() {
        List<MakeBuildAction> actions = new ArrayList<>();
        actions.add( new PlaceShamanOnHexAction(pieceBoard));
        actions.add( new ScoreShamanOnHex(scoreManager));
        return actions;
    }
}
