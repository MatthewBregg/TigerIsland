package piece;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mbregg on 3/15/17.
 */
public class VillagerTest {
    @Test
    public void acceptTest() throws Exception {
        Villager villager = new Villager();
        villager.accept(new PieceVisitor() {
            public void visitVillager(Villager v) {
                assert(v != null);
            }
            public void visitTotoro(Totoro t) {
                assert(false);
            }
        });
    }
}