package tigerislandserver.adapter;

import tigerisland.game.GameManager;

public class InputAdapterDebugObject {


    private GameManager gm;

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    private String test;


    public GameManager getGm() {
        return gm;
    }
    public void setGm(GameManager gm) {
        this.gm = gm;
    }


}
