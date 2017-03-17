package tigerisland.piece;

/**
 * Created by mbregg on 3/15/17.
 */
public class TotoroCounter implements PieceVisitor {
    private int count = 0;

    int getCount() {
        return count;
    }


    @Override
    public void visitVillager(Villager villager) {
        return;
    }

    @Override
    public void visitTotoro(Totoro totoro) {
        ++count;
        return;
    }
}
