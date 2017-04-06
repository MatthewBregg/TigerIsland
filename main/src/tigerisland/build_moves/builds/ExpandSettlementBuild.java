package tigerisland.build_moves.builds;

import tigerisland.build_moves.SettlementExpansionUtility;
import tigerisland.build_moves.actions.ExpandSettlementOnHexAction;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.rules.BuildActionRule;
import tigerisland.build_moves.rules.EnoughVillagersToExpandRule;
import tigerisland.piece.PieceBoard;
import tigerisland.terrains.Volcano;

import java.util.ArrayList;
import java.util.List;

public class ExpandSettlementBuild extends BuildAction {
    private SettlementExpansionUtility settlementExpansionUtility;
    private PieceBoard pieceBoard;

    public ExpandSettlementBuild(SettlementExpansionUtility settlementExpansionUtility, PieceBoard pieceBoard) {
        this.settlementExpansionUtility = settlementExpansionUtility;
        this.pieceBoard = pieceBoard;
    }

    @Override
    protected List<BuildActionRule> createBuildActionRules() {
        List<BuildActionRule> rules  = new ArrayList<BuildActionRule>();
        rules.add(new BuildActionRule() {
            // Check if expanding onto volcano type or if terrain
            @Override
            public BuildActionResult applyRule(BuildActionData buildActionData) {
               if ( buildActionData.getExpansionTerrain().equals(Volcano.getInstance())) {
                   return new BuildActionResult(false, "Cannot expand onto volcano!!");
               } else {
                   return new BuildActionResult(true, "");
               }
            }
        });
        rules.add(new EnoughVillagersToExpandRule(settlementExpansionUtility));
        rules.add(new BuildActionRule() {
            // Check if settlement exists.
            @Override
            public BuildActionResult applyRule(BuildActionData buildActionData) {
                if ( pieceBoard.isLocationOccupied(buildActionData.getSettlementToExpandFromLocation(),
                        buildActionData.getPlayer().getId())) {
                    return new BuildActionResult(true,"");
                } else {
                    return new BuildActionResult(false, "No settlement to expand be located here!");
                }
            }
        });
        return rules;
    }

    @Override
    protected List<MakeBuildAction> createBuildActions() {
        List<MakeBuildAction> actions = new ArrayList<MakeBuildAction>();
        actions.add ( new ExpandSettlementOnHexAction(pieceBoard,settlementExpansionUtility));
        return actions;
    }
}
