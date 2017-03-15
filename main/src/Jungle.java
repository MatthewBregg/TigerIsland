
public class Jungle {
	private static Jungle instance;

	private Jungle(){}

	public static Jungle instance(){
		if(instance == null)
		{
			instance=new Jungle();
		}

		return instance;
	}
}
