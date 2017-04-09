package tigerislandserver.server;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

public class TextFileReader
{
    HashMap<String, String> usernameAndPasswords = null;
    String usernameAndPasswordFileName;

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
                try
                {
                    Scanner scan = new Scanner(filePath);
                    while (scan.hasNextLine())
                    {
                        String userInfo = scan.nextLine();
                        String username = getUsername(userInfo);
                        String password = getPassword(userInfo);

                        usernameAndPasswords.put(username, password);
                    }

                } catch (IOException ioexception)
                {
                    System.out.println("Did not find file, looked in:" + filePath.toAbsolutePath());
                }
            }
        }

        return usernameAndPasswords;
    }

    private String getUsername(String userInfo)
    {
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine[0];
    }

    private String getPassword(String userInfo)
    {
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine[1];
    }
}