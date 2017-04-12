package tigerisland.datalogger;

public class MatchRow {

    private int match_id;
    private int p1_id;
    private int p2_id;
    private int game_id;
    private int challenge_id;
    private String status;

    public MatchRow(int p1_id, int p2_id, int game_id, int challenge_id, int match_id, String status) {
        this.p1_id = p1_id;
        this.p2_id = p2_id;
        this.game_id = game_id;
        this.match_id = match_id;

        this.challenge_id = challenge_id;
        this.status = status;
    }

    public int getChallenge_id() {
        return challenge_id;
    }

    public String getStatus() {
        return status;
    }

    public int getGame_id() {
        return game_id;
    }

    public int getP2_id() {
        return p2_id;
    }

    public int getP1_id() {
        return p1_id;
    }

    public int getMatch_id() {
        return match_id;
    }
}


