package Piece;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mbregg on 3/15/17.
 */
public class TotoroTest {
    @Test
    public void acceptTest() throws Exception {
        Totoro totoro = new Totoro();
        totoro.accept(new PieceVisitor() {
            public void visitTotoro(Totoro t) {
                assert(t != null);
            }
            public void visitVillager(Villager v) {
                assert(false);
            }
        });
    }
}