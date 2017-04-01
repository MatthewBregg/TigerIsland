package tigerislandserver.server;

import org.junit.*;

import javax.xml.soap.Text;
import java.util.HashMap;

/**
 * Created by christinemoore on 4/1/17.
 */
public class TextFileReaderTest {
    public static final String fileLocation = "/Users/christinemoore/Desktop/usernamePasswords.rtf";
    public static TextFileReader tfr;
    public static HashMap<String, String> usernamesAndPasswords;

    @BeforeClass
    public static void generateTextFileReader(){
        tfr = new TextFileReader(fileLocation);
    }

    @Test
    public void doesFileExist(){
        boolean result = tfr.doesFileExist();
        Assert.assertTrue(result);
    }

    @Test
    public void isHashMapRightSize(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        System.out.println(usernamesAndPasswords.size());
        Assert.assertTrue(usernamesAndPasswords.size() == 2);
    }

    @Test
    public void correctlyReadInFirstUserPassword(){
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String firstUsername = "phil";
        Assert.assertTrue(usernamesAndPasswords.get(firstUsername) == "allen");
    }



}
