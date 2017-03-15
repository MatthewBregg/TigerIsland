package Piece;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mbregg on 3/15/17.
 */
public class TotoroCounterTest {
    @Test
    public void whenCreatedThenCountZero() {
        TotoroCounter tc = new TotoroCounter();
        assert(tc.getCount() == 0);
    }

    @Test
    public void whenTotoroThenIncrementCount() {
        Piece p = new Totoro();
        TotoroCounter tc = new TotoroCounter();
        p.accept(tc);
        assert(tc.getCount() == 1);
    }

    @Test
    public void whenVillageThenDoNotIncrement() {
        Piece p = new Villager();
        TotoroCounter tc = new TotoroCounter();
        p.accept(tc);
        assert(tc.getCount() == 0);
    }
}
