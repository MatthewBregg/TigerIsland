package tigerisland.piece;


public abstract class PieceVisitor {
    public void visitVillager(Villager villager) {
        // Do nothing by default
    }

    public void visitTotoro(Totoro totoro) {
        // Do nothing by default
    }

    public void visitNullPiece(NullPiece nullPiece) {
        // Do nothing by default
    }
}
