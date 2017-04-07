package tigerisland.tile_placement.rules;

import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceVisitor;
import tigerisland.piece.Tiger;
import tigerisland.piece.Totoro;
import tigerisland.tile_placement.exceptions.NukeTigerRuleException;
import tigerisland.tile_placement.exceptions.NukeTotoroRuleException;
import tigerisland.tile_placement.exceptions.TilePlacementException;

import java.util.Map;
import java.util.Set;

class NukablePieceVistor extends PieceVisitor {
    private boolean hasTiger = false;
    private boolean hasTotoro = false;

    @Override
    public void visitTiger(Tiger t) {
        hasTiger = true;
    }

    @Override
    public void visitTotoro(Totoro t) {
        hasTotoro = true;
    }

    public boolean getHasTiger() {
        return hasTiger;
    }

    public boolean getHasTotoro() {
        return hasTotoro;
    }
};

public class NukeNonNukeablePieceRule implements NukePlacementRule {
    PieceBoard pieces;

    public NukeNonNukeablePieceRule(PieceBoard pieceBoard) {
        this.pieces = pieceBoard;
    }

    @Override
    public void applyRule(Map<Location, Hex> hexes) throws TilePlacementException {

        isThereATotoroAtLocations(hexes.keySet());

    }

    private void isThereATotoroAtLocations(Set<Location> locations) throws TilePlacementException {
        NukablePieceVistor pieceVisitor = new NukablePieceVistor();
        for (Location location : locations) {

            pieces.getPiece(location).accept(pieceVisitor);

        }
        if (pieceVisitor.getHasTiger()) {
            throw new NukeTigerRuleException();
        }
        if (pieceVisitor.getHasTotoro()) {
            throw new NukeTotoroRuleException();
        }

    }

}
