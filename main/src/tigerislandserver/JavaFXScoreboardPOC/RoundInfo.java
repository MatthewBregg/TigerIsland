package tigerislandserver.JavaFXScoreboardPOC;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RoundInfo {



    public int getCurrentRound() {
        return currentRound.get();
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public int getCurrentChallenge() {
        return currentChallenge.get();
    }

    public IntegerProperty currentChallengeProperty() {
        return currentChallenge;
    }

    public int getEndRound() {
        return endRound.get();
    }

    public IntegerProperty endRoundProperty() {
        return endRound;
    }

    public int getEndChallenge() {
        return endChallenge.get();
    }

    public IntegerProperty endChallengeProperty() {
        return endChallenge;
    }

    private IntegerProperty currentRound;
    private IntegerProperty currentChallenge;
    private IntegerProperty endRound;
    private IntegerProperty endChallenge;

    public static RoundInfo getRoundInfoInstance(){
        return new RoundInfo();
    }


    public void setEndRound(int endRound) {
        this.endRound.set(endRound);
    }

    public void setEndChallenge(int endChallenge) {
        this.endChallenge.set(endChallenge);
    }

    public RoundInfo(){
        currentRound = new SimpleIntegerProperty(-1);
        currentChallenge = new SimpleIntegerProperty(-2);
        endRound = new SimpleIntegerProperty(-3);
        endChallenge = new SimpleIntegerProperty(-4);
//        initializeColumns();
    }

    public void initializeColumns(){
        resetCurrentRound();
        resetCurrentChallenge();

    }


    private void resetCurrentChallenge() {
        setCurrentChallenge(0);
    }


    private void resetCurrentRound() {
        setCurrentRound(0);
    }

    public void setCurrentChallenge(int chal) {
        this.currentChallenge.set(chal);
    }

    public void setCurrentRound(int round){
        this.currentRound.set(round);
    }








}

//
//    ty("Current Round Number");
//    operty("Current Challenge Number");
//    Final Round Number");
//        ty("End Challenge Number");