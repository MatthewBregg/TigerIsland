package tigerislandserver.JavaFXScoreboardPOC;


import javafx.beans.property.*;

public class TournamentScore {
    private StringProperty teamName;
    private StringProperty opponentName;
    private StringProperty statusGameA;

    private StringProperty statusGameB;
    private IntegerProperty tourneyScore, challengeScore, scoreGameA, scoreGameB,
            villagerGameA, villagerGameB,totoroGameA, totoroGameB, tigerGameA, tigerGameB;

    public TournamentScore(String teamName){
        this.teamName = new SimpleStringProperty(teamName);
        opponentName = new SimpleStringProperty("");
        statusGameA = new SimpleStringProperty("Not Yet Playing");
        statusGameB = new SimpleStringProperty("Not Yet Playing");

        tourneyScore = new SimpleIntegerProperty(0);
        challengeScore = new SimpleIntegerProperty(0);
        scoreGameA = new SimpleIntegerProperty(0);
        scoreGameB = new SimpleIntegerProperty(0);
        villagerGameA = new SimpleIntegerProperty(0);
        villagerGameB = new SimpleIntegerProperty(0);
        totoroGameA = new SimpleIntegerProperty(0);
        totoroGameB = new SimpleIntegerProperty(0);
        tigerGameA = new SimpleIntegerProperty(0);
        tigerGameB = new SimpleIntegerProperty(0);

        initialize();
    }

    private void initialize(){
        resetTourneyScore();
        resetChallengeScore();
        resetOpponent();
        resetGameA();
        resetGameB();
    }

    public String getTeamName() {
        return teamName.get();
    }

    public StringProperty teamNameProperty() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public String getOpponentName() {
        return opponentName.get();
    }

    public StringProperty opponentNameProperty() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName.set(opponentName);
    }

    public String getStatusGameA() {
        return statusGameA.get();
    }

    public StringProperty statusGameAProperty() {
        return statusGameA;
    }

    public void setStatusGameA(String statusGameA) {
        this.statusGameA.set(statusGameA);
    }

    public String getStatusGameB() {
        return statusGameB.get();
    }

    public StringProperty statusGameBProperty() {
        return statusGameB;
    }

    public void setStatusGameB(String statusGameB) {
        this.statusGameB.set(statusGameB);
    }

    public int getTourneyScore() {
        return tourneyScore.get();
    }

    public IntegerProperty tourneyScoreProperty() {
        return tourneyScore;
    }

    public void setTourneyScore(int tourneyScore) {
        this.tourneyScore.set(tourneyScore);
    }

    public int getChallengeScore() {
        return challengeScore.get();
    }

    public IntegerProperty challengeScoreProperty() {
        return challengeScore;
    }

    public void setChallengeScore(int challengeScore) {
        this.challengeScore.set(challengeScore);
    }

    public int getScoreGameA() {
        return scoreGameA.get();
    }

    public IntegerProperty scoreGameAProperty() {
        return scoreGameA;
    }

    public void setScoreGameA(int scoreGameA) {
        this.scoreGameA.set(scoreGameA);
    }

    public int getScoreGameB() {
        return scoreGameB.get();
    }

    public IntegerProperty scoreGameBProperty() {
        return scoreGameB;
    }

    public void setScoreGameB(int scoreGameB) {
        this.scoreGameB.set(scoreGameB);
    }

    public int getVillagerGameA() {
        return villagerGameA.get();
    }

    public IntegerProperty villagerGameAProperty() {
        return villagerGameA;
    }

    public void setVillagerGameA(int villagerGameA) {
        this.villagerGameA.set(villagerGameA);
    }

    public int getVillagerGameB() {
        return villagerGameB.get();
    }

    public IntegerProperty villagerGameBProperty() {
        return villagerGameB;
    }

    public void setVillagerGameB(int villagerGameB) {
        this.villagerGameB.set(villagerGameB);
    }

    public int getTotoroGameA() {
        return totoroGameA.get();
    }

    public IntegerProperty totoroGameAProperty() {
        return totoroGameA;
    }

    public void setTotoroGameA(int totoroGameA) {
        this.totoroGameA.set(totoroGameA);
    }

    public int getTotoroGameB() {
        return totoroGameB.get();
    }

    public IntegerProperty totoroGameBProperty() {
        return totoroGameB;
    }

    public void setTotoroGameB(int totoroGameB) {
        this.totoroGameB.set(totoroGameB);
    }

    public int getTigerGameA() {
        return tigerGameA.get();
    }

    public IntegerProperty tigerGameAProperty() {
        return tigerGameA;
    }

    public void setTigerGameA(int tigerGameA) {
        this.tigerGameA.set(tigerGameA);
    }

    public int getTigerGameB() {
        return tigerGameB.get();
    }

    public IntegerProperty tigerGameBProperty() {
        return tigerGameB;
    }

    public void setTigerGameB(int tigerGameB) {
        this.tigerGameB.set(tigerGameB);
    }

    public void resetTourneyScore(){
        setTourneyScore(0);
    }

    public void resetChallengeScore(){
        setChallengeScore(0);
    }

    public void addPointsGameA(int points){
        int oldScore = getScoreGameA();
        setScoreGameA(oldScore + points);
    }

    public void wonGameA(){
        setStatusGameA("WIN");
    }

    public void forfeitGameA(){
        setStatusGameA("FORFEIT");
    }

    public void lostGameA(){
        setStatusGameA("LOSS");
    }

    public void addPointsGameB(int points){
        int oldScore = getScoreGameB();
        setScoreGameB(oldScore + points);
    }

    public void wonGameB(){
        setStatusGameB("WIN");
    }

    public void forfeitGameB(){
        setStatusGameB("FORFEIT");
    }

    public void lostGameB(){
        setStatusGameB("LOSS");
    }

    public void resetOpponent(){
        setOpponentName("N/A");
    }

    public void resetGameA(){
        setStatusGameA("NOT PLAYING");
        setScoreGameA(0);
        setVillagerGameA(0);
        setTotoroGameA(0);
        setTigerGameA(0);
    }

    public void resetGameB(){
        setStatusGameB("NOT PLAYING");
        setScoreGameB(0);
        setVillagerGameB(0);
        setTotoroGameB(0);
        setTigerGameB(0);
    }

    public void addToChallengeScore(int points){
        int challengeScore = getChallengeScore();
        challengeScore += points;
        setChallengeScore(challengeScore);
    }

    public void addChallengeScoreToTournament(){
        int challengeScore = getChallengeScore();
        int tournamentScore = getTourneyScore();
        resetChallengeScore();
        setTourneyScore(challengeScore + tournamentScore);
    }
}
