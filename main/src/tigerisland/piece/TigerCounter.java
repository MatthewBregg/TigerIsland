package tigerisland.piece;

public class TigerCounter extends PieceVisitor {

    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public void visitTiger(Tiger tiger) {
        ++count;
        return;
    }
}
