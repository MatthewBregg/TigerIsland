package tigerislandserver.server;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class TextFileReader
{
    private HashMap<String, String> usernameAndPasswords = null;
    private String usernameAndPasswordFileName;
    private int invalidLinesFound = 0;

    public int getInvalidLinesFound() { return invalidLinesFound; }
    public TextFileReader(String fileName)
    {
        this.usernameAndPasswordFileName = fileName;
    }

    public HashMap<String, String> getUsernameAndPasswordCombos()
    {
        if (usernameAndPasswords == null)
        {
            synchronized (TextFileReader.class)
            {
                if (usernameAndPasswords != null)
                {
                    return usernameAndPasswords;
                }

                usernameAndPasswords = new HashMap<String, String>();

                Path filePath = FileSystems.getDefault().getPath(usernameAndPasswordFileName);
                System.out.println(filePath.toString());
                try
                {
                    Scanner scan = new Scanner(filePath);
                    while (scan.hasNextLine())
                    {
                        String userInfo = scan.nextLine();
                        String[] wordsPerLine = splitOnSpace(userInfo);
                        if ( lineContainsOnlyUserNameAndPass(wordsPerLine) ) {
                            String username = getUsername(wordsPerLine);
                            String password = getPassword(wordsPerLine);
                            usernameAndPasswords.put(username, password);
                        } else {
                            ++invalidLinesFound;
                        }

                    }

                } catch (IOException ioexception)
                {
                    System.out.println("Did not find file, looked in:" + filePath.toAbsolutePath());
                }
            }
        }

        return usernameAndPasswords;
    }

    private boolean lineContainsOnlyUserNameAndPass(String[] wordsPerLine) {
        return wordsPerLine.length == 2;
    }

    private String[] splitOnSpace(String userInfo) {
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine;
    }

    private String getUsername(String[] wordsPerLine)
    {
        return wordsPerLine[0];
    }

    private String getPassword(String[] wordsPerLine)
    {
        return wordsPerLine[1];
    }
}