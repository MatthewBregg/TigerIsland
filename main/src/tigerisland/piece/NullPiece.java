package tigerisland.piece;

public class NullPiece implements Piece {
    @Override
    public void accept(PieceVisitor visitor) {
        visitor.visitNullPiece(this);
    }
}
