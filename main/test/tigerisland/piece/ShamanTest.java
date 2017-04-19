package tigerisland.piece;

import org.junit.Assert;
import org.junit.Test;

public class ShamanTest {
    @Test
    public void acceptTest() throws Exception {
        Shaman shaman = new Shaman();
        shaman.accept(new PieceVisitor() {
            public void visitShaman(Shaman s) {
                Assert.assertTrue(s != null);
            }
            public void visitVillager(Villager v) {
                Assert.assertTrue(false);
            }
        });
    }
}