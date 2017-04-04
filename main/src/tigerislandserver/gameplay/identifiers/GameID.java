package tigerislandserver.gameplay.identifiers;


public class GameID {
    private char currentID;

    public GameID(char playerLetter){
        currentID = playerLetter;
    }

    public long getID(){
        return currentID;
    }
}
