package tigerislandserver.adapter;

import tigerisland.game.GameManager;

public class InputAdapterDebugObject {


    private GameManager initialGM;

    private GameManager currentGM;

    public void setTest(String test) {
        this.move = test;
    }

    public String getTest() {
        return move;
    }

    private String move;


    public void setInitialGM(GameManager initialGM) {
        this.initialGM = initialGM;
    }
    public void setCurrentGM(GameManager currentGM) {
        this.currentGM = currentGM;    }

    public GameManager getCurrentGM() {
        return currentGM;
    }
    public GameManager getInitialGM() {
        return initialGM;
    }


}
