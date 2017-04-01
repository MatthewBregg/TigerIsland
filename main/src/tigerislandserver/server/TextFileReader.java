package tigerislandserver.server;

import java.io.File;
import java.io.FileNotFoundException;
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
        try {
            if (doesFileExist()){
                Scanner scan = new Scanner(new File(usernameAndPasswordFileName));
                while (scan.hasNextLine()) {
                    String userInfo = scan.nextLine();

                    String username = getUsername(userInfo);
                    String password = getPassword(userInfo);

                    usernameAndPasswords.put(username, password);
                }
            }
            else{
                System.out.println("file does not exist");
            }
        } catch (FileNotFoundException exc) {
            System.out.println(" Sorry, your filename of " + usernameAndPasswordFileName + " was not found.");
        }

        return usernameAndPasswords;
    }

    public boolean doesFileExist(){
        File potentialFiles = new File(usernameAndPasswordFileName);
        if (potentialFiles == null){
            return false;
        }
        else{
            return true;
        }
    }

    public String getUsername(String userInfo) {
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        return wordsPerLine[0];
    }

    public String getPassword(String userInfo){
        String[] wordsPerLine;
        wordsPerLine = userInfo.split("\\s+");
        System.out.println("Size of words perLine " + wordsPerLine[0]);
        return wordsPerLine[1];
    }
}

















