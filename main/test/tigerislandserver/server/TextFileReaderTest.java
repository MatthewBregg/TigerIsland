package tigerislandserver.server;

import org.junit.*;

import javax.xml.soap.Text;
import java.util.HashMap;

public class TextFileReaderTest {
    public String fileLocation;
    private final String directory = "test_text_files/";
    public TextFileReader tfr;
    public HashMap<String, String> usernamesAndPasswords;


    public void generateTextFileReader(){
        tfr = new TextFileReader(fileLocation);
    }

    public void setUrlToStandardUserPassFile() {
        fileLocation = directory+ "testUsernamesPasswords.txt";
    }

    public void setUrlToFileWithTrailingLineEnd() {
        fileLocation = directory+"testUsernamesPasswordsTrailingLineEnd.txt";
    }

    public void setUrlToFileWithBlankLine() {
        fileLocation = directory+"testUsernamesPasswordsBlankLine.txt";
    }

    public void setUrlToFileWithInvalidLine() {
        fileLocation = directory+"testUsernamesPasswordsInvalidLine.txt";
    }

    public void setUpStandardUserPassFile() {
        setUrlToStandardUserPassFile();;
        generateTextFileReader();
    }

    @Test
    public void isHashMapRightSize(){
        setUpStandardUserPassFile();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertTrue(usernamesAndPasswords.size() == 4);
    }

    @Test
    public void correctlyReadInFirstUserPassword(){
        setUpStandardUserPassFile();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String firstUsername = "A";
        Assert.assertTrue(usernamesAndPasswords.get(firstUsername).equals("A"));
    }

    @Test
    public void correctlyReadInSecondUserPassword(){
        setUpStandardUserPassFile();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String secondPlayer = "B";
        Assert.assertTrue(usernamesAndPasswords.get(secondPlayer).equals("B"));
    }

    @Test
    public void shouldNotReadInUserPassword()
    {
        setUpStandardUserPassFile();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        String secondPlayer = "b";
        Assert.assertNotEquals("b",usernamesAndPasswords.get(secondPlayer));
    }

    @Test
    public void noLinesInvalidFromValidFile()
    {
        setUpStandardUserPassFile();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertEquals(0,tfr.getInvalidLinesFound());
    }

    @Test public void blankLineInMiddleStillReads() {
        setUrlToFileWithBlankLine();
        generateTextFileReader();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertEquals(2,usernamesAndPasswords.size());
        Assert.assertEquals("BAR",usernamesAndPasswords.get("FOO"));
        Assert.assertEquals("FOO",usernamesAndPasswords.get("BAR"));
        Assert.assertEquals(1,tfr.getInvalidLinesFound());
    }

    @Test public void trailingLineEndStillReads() {
        setUrlToFileWithTrailingLineEnd();
        generateTextFileReader();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertEquals(1,usernamesAndPasswords.size());
        Assert.assertEquals("BAR",usernamesAndPasswords.get("FOO"));
        Assert.assertEquals(1,tfr.getInvalidLinesFound());
    }

    @Test public void invalidLineStillReads() {
        setUrlToFileWithInvalidLine();
        generateTextFileReader();
        usernamesAndPasswords = tfr.getUsernameAndPasswordCombos();
        Assert.assertEquals(1,usernamesAndPasswords.size());
        Assert.assertEquals("BAR",usernamesAndPasswords.get("FOO"));
        Assert.assertEquals(1,tfr.getInvalidLinesFound());
    }




}
