package tigerislandserver.server;

import org.junit.*;

import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by christinemoore on 4/1/17.
 */
public class TextFileReaderTest {
    public static final String fileLocation = "main/src/usernamesPasswords.txt";
    public static TextFileReader tfr;
    public static HashMap<String, String> usernamesAndPasswords;

    @BeforeClass
    public static void generateTextFileReader(){
        tfr = new TextFileReader(fileLocation);
    }

    @Test
    public void isHashMapRightSize(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertTrue(usernamesAndPasswords.size() == 2);
    }

    @Test
    public void correctlyReadInFirstUserPassword(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String firstUsername = "phil";
        Assert.assertTrue(usernamesAndPasswords.get(firstUsername).equals("allen"));
    }

    @Test
    public void correctlyReadInSecondUserPassword(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String firstUsername = "christine";
        Assert.assertTrue(usernamesAndPasswords.get(firstUsername).equals("moore"));
    }



}
