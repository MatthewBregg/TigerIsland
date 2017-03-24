package tigerisland.piece;

/**
 * Created by christinemoore on 3/24/17.
 */
public class Tiger implements Piece{
    public void accept(PieceVisitor visitor) {
        visitor.visitTiger(this);
    }
}

