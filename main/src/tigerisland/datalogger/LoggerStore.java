package tigerisland.datalogger;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class LoggerStore {
    private static class ID {
        int gameid;
        int challengeid;
        public ID(int gameid, int challengeid) {
            this.gameid = gameid;
            this.challengeid = challengeid;
        }
    }
        public static HashMap<ID,DataLogger> loggers;
        synchronized public static DataLogger getLogger(int gameid, int challengeid) {
            ID id = new ID(gameid,challengeid);
            DataLogger logger = loggers.get(id);
            if  ( logger == null ) {
                loggers.put(id,new ConsoleLogger(gameid,challengeid));
            }
            return logger;
        }
}
