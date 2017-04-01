package tigerislandserver.server.identifiers;

public class IdentityGenerator {
    private int currentID;

    public IdentityGenerator(){
        currentID = 1;
    }

    public int getID(){
        int newID = currentID;
        ++currentID;
        return newID;
    }
}
