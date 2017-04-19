package tigerisland.piece;

/**
 * Created by christinemoore on 4/17/17.
 */
public class Shaman implements Piece{
    public void accept(PieceVisitor visitor) {
        visitor.visitShaman(this);
    }
}
