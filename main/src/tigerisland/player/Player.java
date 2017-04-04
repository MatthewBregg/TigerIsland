package tigerisland.player;

public class Player
{
    private PlayerID id;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;

    public Player(int villagerCount, int totoroCount, int tigerCount, PlayerID pID)
    {
        id = pID;
        this.villagerCount = villagerCount;
        this.totoroCount = totoroCount;
        this.tigerCount = tigerCount;
    }



    public Player()
    {
        this(20, 3, 2, new PlayerID());
    }

    public PlayerID getId()
    {
        return id;
    }

    public int getVillagerCount()
    {
        return villagerCount;
    }

    public int getTotoroCount()
    {
        return totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }

    public boolean removeVillagers(int quantity)
    {
        if(villagerCount < quantity)
        {
            return false;
        }

        villagerCount -= quantity;
        return true;
    }

    public boolean removeVillager()
    {
        return removeVillagers(1);
    }

    public boolean removeTotoro()
    {
        if(totoroCount == 0)
        {
            return false;
        }

        totoroCount--;
        return true;
    }

    public boolean removeTiger(){
        if (tigerCount == 0){
            return false;
        }

        tigerCount--;
        return true;
    }
}
