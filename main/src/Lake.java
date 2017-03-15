
public class Lake {
	private static Lake instance;

	private Lake(){}

	public static Lake instance(){
		if(instance == null)
		{
			instance=new Lake();
		}

		return instance;
	}
}
