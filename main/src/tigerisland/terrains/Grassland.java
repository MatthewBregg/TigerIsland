package tigerisland.terrains;

public class Grassland extends Terrain {
	private static Grassland instance;
	
	private Grassland(){}
	
	public static Grassland getInstance(){
		if(instance == null)
		{
			instance=new Grassland();
		}
		
		return instance;
	}

	public String toString()
	{
		return "GRASS";
	}
}
