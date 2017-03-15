
public class Player {
	private PlayerID id;
	private int villagerCount;
	private int totoroCount;
	
	public Player(int villagerCount, int totoroCount)
	{
		id=new PlayerID();
		this.villagerCount=villagerCount;
		this.totoroCount=totoroCount;
	}
	
	public Player()
	{
		this(20,3);
	}

	public PlayerID getId() {
		return id;
	}

	public int getVillagerCount() {
		return villagerCount;
	}

	public int getTotoroCount() {
		return totoroCount;
	}
}
