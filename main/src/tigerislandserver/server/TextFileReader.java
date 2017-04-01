package tigerislandserver.server;

import javax.xml.soap.Text;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by christinemoore on 4/1/17.
 */
public class TextFileReader {
    String usernameAndPasswordFileName;
    File usernameAndPasswordFile;


    public TextFileReader(){

    }

    public
    

    public boolean isValidUsernamePasswordCombo(){

    }





















    public class FileReader {

        /**
         * Given a file containing a move on each line, will return the moves as a List.
         *
         * @param filename filename of the file containing the moves
         * @return List of moves
         */
        public static List<String> getMoves(String filename) {
            List<String> moves = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(new File(filename));
                while (scanner.hasNextLine()) {
                    moves.add(scanner.nextLine());
                }
            } catch (FileNotFoundException exc) {
                System.out.println("FATAL: " + filename + " not found.");
            }
            return moves;
        }

        /**
         * Given a file containing a credential on each line, where a credential is a login name and a password separated by a
         * space, will return the credentials as a hashmap
         *
         * @param filename filename of the file containing the credentials
         * @return HashMap of credentials; key=loginName, value=password
         */
        public static HashMap<String,String> getCredentials(String filename) {
            HashMap<String,String> credentials = new HashMap<>();
            try {
                Scanner scanner = new Scanner(new File(filename));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Scanner lineScanner = new Scanner(line);
                    String loginName = "";
                    String password = "";
                    if (lineScanner.hasNext()) {
                        loginName = lineScanner.next();
                    }
                    if (lineScanner.hasNext()) {
                        password = lineScanner.next();
                    }
                    credentials.put(loginName, password);
                }
            } catch (FileNotFoundException exc) {
                System.out.println("FATAL: " + filename + " not found.");
            }
            return credentials;
        }

        /**
         * Given a file containing a credential on each line, where a credential is a login name and a password separated by a
         * space, will return the login names as a list
         *
         * @param filename filename of the file containing the credentials
         * @return List of login names
         */
        public static List<String> getLoginNames(String filename) {
            List<String> loginNames = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(new File(filename));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Scanner lineScanner = new Scanner(line);
                    String loginName = "";
                    if (lineScanner.hasNext()) {
                        loginName = lineScanner.next();
                    }
                    if (lineScanner.hasNext()) {
                        lineScanner.next(); // do nothing with this token
                    }
                    loginNames.add(loginName);
                }
            } catch (FileNotFoundException exc) {
                System.out.println("FATAL: " + filename + " not found.");
            }
            return loginNames;
        }
    }
    Contact GitHub API Training Shop Blog About
© 2017 GitHub, Inc. Terms Privacy Security Status Help
}
