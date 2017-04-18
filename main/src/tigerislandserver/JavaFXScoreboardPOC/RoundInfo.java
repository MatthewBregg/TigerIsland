package tigerislandserver.JavaFXScoreboardPOC;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RoundInfo {
    private static RoundInfo instance;
    private IntegerProperty currentRound;
    private IntegerProperty currentChallenge;
    private IntegerProperty endRound;
    private IntegerProperty endChallenge;
    private StringProperty roundStatement;
    private StringProperty challengeStatement;

    public static RoundInfo getInstance()
    {
        if(instance == null)
        {
            synchronized(RoundInfo.class)
            {
                if(instance != null)
                {
                    return instance;
                }

                instance =new RoundInfo();
            }
        }

        return instance;
    }

    private RoundInfo(){
        currentRound = new SimpleIntegerProperty(-1);
        currentChallenge = new SimpleIntegerProperty(-2);
        endRound = new SimpleIntegerProperty(-3);
        endChallenge = new SimpleIntegerProperty(-4);
        roundStatement = new SimpleStringProperty();
        challengeStatement = new SimpleStringProperty();
//        initializeColumns();
        updateChallengeStatement();
        updateRoundStatement();
    }

    public void initializeColumns(){
        resetCurrentRound();
        resetCurrentChallenge();

    }

    private void updateRoundStatement(){
        String statement = ("Round " + (currentRound.get()) + " of " + (endRound.get()));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                roundStatement.set(statement);
            }
        });
    }

    private void updateChallengeStatement(){
        String statement = ("Challenge " + (currentChallenge.get() + 1) + " of " + (endChallenge.get() + 1));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                challengeStatement.set(statement);
            }
        });
    }

    public StringProperty roundStatementProperty(){
        return roundStatement;
    }

    public String getRoundStatement(){
        return roundStatement.get();
    }

    public StringProperty challengeStatementProperty(){
        return challengeStatement;
    }

    public String getChallengeStatement(){
        return challengeStatement.get();
    }

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

    public static RoundInfo getRoundInfoInstance(){
        return new RoundInfo();
    }

    public void setEndRound(int endRound) {
        this.endRound.set(endRound);
        updateRoundStatement();
    }

    public void setEndChallenge(int endChallenge) {
        this.endChallenge.set(endChallenge);
        updateChallengeStatement();
    }

    private void resetCurrentChallenge() {
        setCurrentChallenge(0);
        updateChallengeStatement();
    }

    private void resetCurrentRound() {
        setCurrentRound(0);
        updateRoundStatement();
    }

    public void setCurrentChallenge(int chal) {
        this.currentChallenge.set(chal);
        updateChallengeStatement();
    }

    public void setCurrentRound(int round){
        this.currentRound.set(round);
        updateRoundStatement();
    }
}

