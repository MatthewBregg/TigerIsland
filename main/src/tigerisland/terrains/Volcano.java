package tigerisland.terrains;

public class Volcano extends Terrain {
	private static Volcano instance;
	
	private Volcano(){}
	
	public static Volcano getInstance(){
		if(instance == null)
		{
			instance=new Volcano();
		}
		
		return instance;
	}

	public String toString()
	{
		return "VOLCANO";
	}
}
