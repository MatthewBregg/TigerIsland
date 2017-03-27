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

    private Board board;
    private PieceBoard pieceBoard;

    public FoundNewSettlementBuild(Board board, PieceBoard pieceBoard) {
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

}
