
public class Grassland {
	private static Grassland instance;
	
	private Grassland(){}
	
	public static Grassland instance(){
		if(instance == null)
		{
			instance=new Grassland();
		}
		
		return instance;
	}
}
