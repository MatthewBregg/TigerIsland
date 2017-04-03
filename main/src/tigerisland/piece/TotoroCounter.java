package tigerisland.piece;


public class TotoroCounter extends PieceVisitor {
    private int count = 0;

    public int getCount() {
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
