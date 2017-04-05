package tigerisland.build_moves.builds;

import tigerisland.board.Board;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceTotoroOnHexAction;
import tigerisland.build_moves.actions.ScoreTotoroOnHex;
import tigerisland.build_moves.rules.*;
import tigerisland.piece.PieceBoard;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.SettlementBoard;

import java.util.ArrayList;
import java.util.List;

public class TotoroBuild extends BuildAction {

    private Board board;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private ScoreManager scoreManager;

    public TotoroBuild(Board board, PieceBoard pieceBoard, SettlementBoard settlementBoard, ScoreManager scoreManager) {
        this.board = board;
        this.pieceBoard = pieceBoard;
        this.settlementBoard = settlementBoard;
        this.scoreManager = scoreManager;

    }

    @Override
    public List<BuildActionRule> createBuildActionRules() {
        List<BuildActionRule> rules = new ArrayList<>();
        rules.add( new PlayerMustHaveATotoroToBuildRule());
        rules.add( new EmptyHexRule(pieceBoard));
        rules.add( new CannotBuildOnVolcanoRule(board));
        rules.add( new TotoroBuildHexAdjacentToSettlementSizeFiveRule(settlementBoard));
        rules.add( new SettlementAlreadyContainsTotoroRule(settlementBoard));
        return rules;
    }

    @Override
    public List<MakeBuildAction> createBuildActions() {
        List<MakeBuildAction> actions = new ArrayList<>();
        actions.add( new PlaceTotoroOnHexAction(pieceBoard));
        actions.add( new ScoreTotoroOnHex(scoreManager));
        return actions;
    }
}
