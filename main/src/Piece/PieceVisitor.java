package Piece;

/**
 * Created by mbregg on 3/15/17.
 */
public interface PieceVisitor {
    public void visitVillager(Villager villager);

    public void visitTotoro(Totoro totoro);
}
