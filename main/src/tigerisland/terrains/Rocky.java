package tigerisland.terrains;

public class Rocky extends Terrain {
	private static Rocky instance;
	
	private Rocky(){}
	
	public static Rocky getInstance(){
		if(instance == null)
		{
			instance=new Rocky();
		}
		
		return instance;
	}

	public String toString()
	{
		return "ROCK";
	}
}
