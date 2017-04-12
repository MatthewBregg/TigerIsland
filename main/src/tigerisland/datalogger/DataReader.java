package tigerisland.datalogger;

import java.util.List;
import java.util.Map;

public interface DataReader {

    List<MatchRow> getAllMatches();

    public Map<Integer, Map<Integer,Integer>> getOverallScore();

}
