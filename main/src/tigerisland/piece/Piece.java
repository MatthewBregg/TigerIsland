package tigerisland.piece;

public interface Piece {
    public void accept(PieceVisitor visitor);
}