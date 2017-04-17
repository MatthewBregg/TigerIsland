package tigerislandserver;

public class TournamentVariables
{
    private static TournamentVariables instance;
    private String password;
    private String fileName;
    private int numberOfChalleges;
    private int randomSeed;

    private TournamentVariables()
    {
        password="password";
        fileName="pass.txt";
    }

    public static TournamentVariables getInstance()
    {
        if(instance == null)
        {
            synchronized(TournamentVariables.class)
            {
                if(instance != null)
                {
                    return instance;
                }

                instance =new TournamentVariables();
            }
        }

        return instance;
    }

    public synchronized void setTournamentPassword(String password)
    {
        this.password=password;
    }

    public synchronized void setUnsernamePasswordFile(String fileName)
    {
        this.fileName = fileName;
    }

    public void setNumberOfChallenges(int numberOfChallenges)
    {
        this.numberOfChalleges = numberOfChallenges;
    }

    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    public synchronized String getTournamentPassword()
    {
        return password;
    }

    public synchronized String getUsernamePasswordFileName()
    {
        return fileName;
    }

    public int getNumberOfChallenges()
    {
        return numberOfChalleges;
    }

    public int getRandomSeed() {
        return randomSeed;
    }
}
