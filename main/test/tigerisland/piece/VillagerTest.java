package tigerisland.piece;

import org.junit.Assert;
import org.junit.Test;


public class VillagerTest {
    @Test
    public void acceptTest() throws Exception {
        Villager villager = new Villager();
        villager.accept(new PieceVisitor() {
            public void visitVillager(Villager v) {
                Assert.assertTrue(v != null);
            }
            public void visitTotoro(Totoro t) {
                Assert.assertTrue(false);
            }
        });
    }
}