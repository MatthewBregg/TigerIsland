package tigerislandserver.server;

import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.LocalFilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by christinemoore on 4/1/17.
 */
public class TextFileReader {
    HashMap<String, String> usernameAndPasswords;
    String usernameAndPasswordFileName;

    public TextFileReader(String fileName) {
        this.usernameAndPasswords = new HashMap<>();
        this.usernameAndPasswordFileName = fileName;
    }

    public HashMap<String, String> getUsernameAndPasswordCombos() {
        Path filePath = FileSystems.getDefault().getPath(usernameAndPasswordFileName);
        try {
            Scanner scan = new Scanner(filePath);
            while (scan.hasNextLine()) {
                String userInfo = scan.nextLine();
                String username = getUsername(userInfo);
                String password = getPassword(userInfo);

                usernameAndPasswords.put(username, password);
            }

        } catch (IOException ioexception) {
            System.out.println("Did not find file, looked in:" + filePath.toAbsolutePath());
        }

        return usernameAndPasswords;
    }

    public String getUsername(String userInfo) {
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine[0];
    }

    public String getPassword(String userInfo){
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine[1];
    }
}

















