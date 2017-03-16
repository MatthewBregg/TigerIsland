
public class Jungle extends Terrain{
	private static Jungle instance;

	private Jungle(){}

	public static Jungle getInstance(){
		if(instance == null)
		{
			instance=new Jungle();
		}

		return instance;
	}
}
