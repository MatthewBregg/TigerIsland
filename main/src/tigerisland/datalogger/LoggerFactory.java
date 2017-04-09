package tigerisland.datalogger;

public class LoggerFactory {

        public static DataLogger getLogger(int gameid, int challengeid) {
            return new ConsoleLogger(gameid,challengeid);
        }
}
