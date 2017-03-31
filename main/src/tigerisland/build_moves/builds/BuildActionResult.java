package tigerisland.build_moves.builds;

public class BuildActionResult {

    public boolean successful;
    public String errorMessage;

    public BuildActionResult(boolean successful) {
       this.successful= successful;
       this.errorMessage = "";
    }

    public BuildActionResult(boolean successful, String errorMessage) {
       this.successful = successful;
       this.errorMessage = errorMessage;
    }
}
