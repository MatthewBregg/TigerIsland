package tigerisland.piece;

import org.junit.Assert;
import org.junit.Test;

public class TotoroTest {
    @Test
    public void acceptTest() throws Exception {
        Totoro totoro = new Totoro();
        totoro.accept(new PieceVisitor() {
            public void visitTotoro(Totoro t) {
                Assert.assertTrue(t != null);
            }
            public void visitVillager(Villager v) {
                Assert.assertTrue(false);
            }
        });
    }
}