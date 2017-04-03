package tigerislandserver.gameplay.identifiers;

import java.util.concurrent.atomic.AtomicLong;

public class MatchID {
    private static AtomicLong currentID;

    private MatchID(){
        currentID = new AtomicLong(0);
    }

    public static long getID(){
        if(currentID == null){
            currentID = new AtomicLong(0);
        } else{
            currentID.addAndGet(1);
        }

        return currentID.get();
    }
}
