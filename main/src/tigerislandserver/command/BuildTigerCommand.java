package tigerislandserver.command;

import tigerisland.board.Location;

public class BuildTigerCommand
{
    private final Location placementLocation;

    public BuildTigerCommand(Location placementLocation)
    {
        this.placementLocation=placementLocation;
    }
}
