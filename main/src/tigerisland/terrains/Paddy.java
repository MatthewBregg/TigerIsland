package tigerisland.terrains;

public class Paddy extends Terrain {
    private static Paddy instance;

    private Paddy(){}

    public static Paddy getInstance(){
        if(instance == null)
        {
            instance=new Paddy();
        }

        return instance;
    }

    public String toString()
    {
        return "PADDY";
    }
}