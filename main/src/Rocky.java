
public class Rocky {
	private static Rocky instance;
	
	private Rocky(){}
	
	public static Rocky instance(){
		if(instance == null)
		{
			instance=new Rocky();
		}
		
		return instance;
	}
}
