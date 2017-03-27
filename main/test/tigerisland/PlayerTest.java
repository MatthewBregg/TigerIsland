package tigerisland;

import org.junit.Before;
import org.junit.Test;
import tigerisland.player.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest
{
    private Player player;

    @Before
    public void setup()
    {
        player=new Player();
    }

    @Test
    public void constructor_manualCountAssignment_properInitialCounts()
    {
<<<<<<< HEAD
        Player player=new Player(100,5,0);
=======
        Player player = new Player(100,5, 8);
>>>>>>> ChristineServer
        assertEquals(100,player.getVillagerCount());
        assertEquals(5,player.getTotoroCount());
        assertEquals(8, player.getTigerCount());
    }

    @Test
    public void getTotoroCount_atStart_Returns3()
    {
        assertEquals(3,player.getTotoroCount());
    }

    @Test
    public void getVillagerCount_atStart_Returns20()
    {
        assertEquals(20,player.getVillagerCount());
    }

    @Test
    public void removeVillager_remove1_successfulRemoval()
    {
        boolean success=player.removeVillager();

        assertEquals(19,player.getVillagerCount());
        assertTrue(success);
    }

    @Test
    public void removeVillager_remove21_failedRemoval()
    {
        boolean success=true;

        for(int i=0; i<20; i++)
        {
            success=player.removeVillager();

            assertEquals(19-i, player.getVillagerCount());
            assertTrue(success);
        }

        assertTrue(success);

        success=player.removeVillager();

        assertEquals(0,player.getVillagerCount());
        assertFalse(success);
    }

    @Test
    public void removeVillagers_remove5_successfulRemoval()
    {
        boolean success=player.removeVillagers(5);

        assertEquals(15,player.getVillagerCount());
        assertTrue(success);
    }

    @Test
    public void removeVillagers_remove25_failedRemoval()
    {
        boolean success=player.removeVillagers(25);

        assertEquals(20,player.getVillagerCount());
        assertFalse(success);
    }

    @Test
    public void removeTotoro_remove1_successfulRemoval()
    {
        boolean success=player.removeTotoro();

        assertEquals(2,player.getTotoroCount());
        assertTrue(success);
    }

    @Test
    public void removeTotoros_remove4_failedRemoval()
    {
        boolean success=true;

        for(int i=0; i<3; i++)
        {
            success=player.removeTotoro();

            assertEquals(2-i, player.getTotoroCount());
            assertTrue(success);
        }

        assertTrue(success);

        success=player.removeTotoro();

        assertEquals(0,player.getTotoroCount());
        assertFalse(success);
    }

    @Test
    public void getID_samePlayer_sameID()
    {
        assertTrue(player.getId().equals(player.getId()));
    }

    @Test
    public void getID_differentPlayer_differentID()
    {
        Player player2=new Player();
        assertFalse(player.getId().equals(player2.getId()));
    }
}
