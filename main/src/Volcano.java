
public class Volcano extends Terrain {
	private static Volcano instance;
	
	private Volcano(){}
	
	public static Volcano instance(){
		if(instance == null)
		{
			instance=new Volcano();
		}
		
		return instance;
	}
}
