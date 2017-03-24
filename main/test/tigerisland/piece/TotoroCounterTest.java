package tigerisland.piece;

import org.junit.Assert;
import org.junit.Test;

public class TotoroCounterTest {
    @Test
    public void whenCreatedThenCountZero() {
        TotoroCounter tc = new TotoroCounter();
        Assert.assertTrue(tc.getCount() == 0);
    }

    @Test
    public void whenTotoroThenIncrementCount() {
        Piece p = new Totoro();
        TotoroCounter tc = new TotoroCounter();
        p.accept(tc);
        Assert.assertTrue(tc.getCount() == 1);
    }

    @Test
    public void whenVillageThenDoNotIncrement() {
        Piece p = new Villager();
        TotoroCounter tc = new TotoroCounter();
        p.accept(tc);
        Assert.assertTrue(tc.getCount() == 0);
    }
}
