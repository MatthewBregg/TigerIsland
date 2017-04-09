package tigerisland.datalogger;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class LoggerFactory {

        synchronized public static DataLogger getLogger(int gameid, int challengeid) {
            return (new ConsoleLogger(gameid,challengeid));
        }
}
