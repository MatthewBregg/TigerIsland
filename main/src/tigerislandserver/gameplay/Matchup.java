package tigerislandserver.gameplay;

public class Matchup{
    private int player1Index;
    private int player2Index;

    public Matchup(int firstPlayerID, int secondPlayerID){
        player1Index = firstPlayerID;
        player2Index = secondPlayerID;
    }

    public int getPlayer1Index(){
        return player1Index;
    }

    public int getPlayer2Index(){
        return player2Index;
    }
}
