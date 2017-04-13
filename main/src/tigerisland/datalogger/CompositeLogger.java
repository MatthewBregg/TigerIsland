package tigerisland.datalogger;

import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeLogger implements DataLogger {
    List<DataLogger> dataLoggerList;
    CompositeLogger(DataLogger ...loggers) {
        dataLoggerList = Arrays.asList(loggers);
    }
    @Override
    public void writePlacedTotoroMove(PlayerID pid, Location loc) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writePlacedTotoroMove(pid,loc);
        }
    }

    @Override
    public void writeFoundedSettlementMove(PlayerID pid, Location loc) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeFoundedSettlementMove(pid,loc);
        }
    }

    @Override
    public void writeExpandedSettlementMove(PlayerID pid, Location loc, String terrain) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeExpandedSettlementMove(pid,loc,terrain);
        }
    }

    @Override
    public void writePlacedTigerMove(PlayerID pid, Location loc) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writePlacedTigerMove(pid,loc);
        }
    }

    @Override
    public void writePlacedTileMove(PlayerID pid, Location loc, Orientation orientation, String tileTerrains) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writePlacedTileMove(pid,loc,orientation,tileTerrains);
        }
    }

    @Override
    public void writeInvalidMoveAttempted(PlayerID pid, String message) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeInvalidMoveAttempted(pid,message);
        }
    }

    @Override
    public void writeMoveResult(String message) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeMoveResult(message);
        }
    }

    @Override
    public void writeGameEnded(PlayerID winner, PlayerID loser,  String matchEndCondition) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeGameEnded(winner,loser, matchEndCondition);
        }
    }

    @Override
    public void writeGameStarted(PlayerID p1, PlayerID p2) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeGameStarted(p1,p2);
        }
    }

    @Override
    public void writeRawRequest(long timeStamp, String message) {
        for ( DataLogger logger : dataLoggerList ) {
            logger.writeRawRequest(timeStamp,message);
        }
    }

    @Override
    public void writeToTournamentScore(PlayerID pid, int score) {

    }

    @Override
    public void writeToGameTurnScore(PlayerID pId, int moveId, int score) {

    }

    @Override
    public void nextTurn() {
        for ( DataLogger logger : dataLoggerList ) {
            logger.nextTurn();
        }
    }
}
