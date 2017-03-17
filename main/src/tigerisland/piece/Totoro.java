package tigerisland.piece;

/**
 * Created by mbregg on 3/15/17.
 */
public class Totoro implements Piece {
    public void accept(PieceVisitor visitor) {
        visitor.visitTotoro(this);
    }
}
