package tigerisland.piece;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TigerCounterTest {

    @Test
    public void test_ShouldProperlyCountTigers() {
        int numTigers = 8;
        TigerCounter tc = new TigerCounter();
        List<Piece> pieces = new ArrayList<>();
        for (int i =0; i<numTigers;++i){
            pieces.add(new Tiger());
            pieces.add(new Villager());
            pieces.add(new Totoro());
        }
        for (Piece piece : pieces)
            piece.accept(tc);
        Assert.assertEquals(numTigers, tc.getCount());
    }



}
