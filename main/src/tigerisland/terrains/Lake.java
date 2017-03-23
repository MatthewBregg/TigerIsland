package tigerisland.terrains;

public class Lake extends Terrain {
	private static Lake instance;

	private Lake(){}

	public static Lake getInstance(){
		if(instance == null)
		{
			instance=new Lake();
		}

		return instance;
	}
}
