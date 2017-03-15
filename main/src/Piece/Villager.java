package Piece;

/**
 * Created by mbregg on 3/15/17.
 */
public class Villager implements Piece {
    public void accept(PieceVisitor visitor) {
        visitor.visitVillager(this);
    }
}
