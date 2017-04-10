package tigerislandserver.server;

import org.junit.*;

import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by christinemoore on 4/1/17.
 */
public class TextFileReaderTest {
    public static final String fileLocation = "src/usernamesPasswords.txt";
    public static TextFileReader tfr;
    public static HashMap<String, String> usernamesAndPasswords;

    @BeforeClass
    public static void generateTextFileReader(){
        tfr = new TextFileReader(fileLocation);
    }

    @Test
    public void isHashMapRightSize(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        System.out.println(usernamesAndPasswords.size());

        Assert.assertTrue(usernamesAndPasswords.size() == 4);
    }

    @Test
    public void correctlyReadInFirstUserPassword(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String firstUsername = "a";
        Assert.assertTrue(usernamesAndPasswords.get(firstUsername).equals("A"));
    }

    @Test
    public void correctlyReadInSecondUserPassword(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String secondPlayer = "b";
        Assert.assertTrue(usernamesAndPasswords.get(secondPlayer).equals("B"));
    }

    @Test
    public void shouldNotReadInUserPassword()
    {
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String secondPlayer = "b";
        Assert.assertFalse(usernamesAndPasswords.get(secondPlayer).equals("b"));
    }



}
