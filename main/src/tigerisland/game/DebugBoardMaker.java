package tigerisland.game;

import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.terrains.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class DebugBoardMaker {
    private Map<Location, List<Object>> hexLocationsAndLevels;
    private String fileName;
    private HexBoard board = new HexBoard();
    private PieceBoard pieces = new PieceBoardImpl();
    private ArrayList<Player> players;

//    public DebugBoardMaker(int [][], int j){
//        for (int i = 0; I
//
//
//
//
//    }


    public DebugBoardMaker(String fileName, ArrayList<Player> players) {
        this.hexLocationsAndLevels = new HashMap<>();
        this.players = players;
        this.fileName = fileName;
        this.getHexInfoFromFile();
    }

    public Map<Location, List<Object>> getHexMap() {
        return hexLocationsAndLevels;
    }

    public HexBoard getBoard() {
        for (Location location : hexLocationsAndLevels.keySet()) {
            Integer level = (Integer) hexLocationsAndLevels.get(location).get(0);
            Integer tileID = (Integer) hexLocationsAndLevels.get(location).get(1);
            Terrain terrain = (Terrain) hexLocationsAndLevels.get(location).get(2);
            Hex hex = new Hex(tileID.intValue(), level.intValue(), terrain);
            hex.setLevel(level.intValue());
            board.placeHex(location, hex);
        }
        return board;
    }
    public PieceBoard getPieces() {
        Piece piece;
        Player player;
        for (Location location : hexLocationsAndLevels.keySet()) {
            piece = (Piece) hexLocationsAndLevels.get(location).get(3);
            if (piece instanceof NullPiece)
                continue;
            player = (Player) hexLocationsAndLevels.get(location).get(4);
            pieces.addPiece(piece, location, player.getId());
        }
        return pieces;
    }

    private void getHexInfoFromFile() {
        Path filePath = FileSystems.getDefault().getPath(fileName);
        try {
            Scanner scan = new Scanner(filePath);
            while (scan.hasNextLine()) {
                String hexInfo = scan.nextLine();

                String[] hexInfoSplit = hexInfo.split(" ");

                Location location = getLocation(hexInfoSplit);
                List<Object> levelAndTerrainInfo = getLevel(hexInfoSplit);

                hexLocationsAndLevels.put(location, levelAndTerrainInfo);
            }

        } catch (IOException ioexception) {
            System.out.println("Did not find file, looked in:" + filePath.toAbsolutePath());
        }
    }

    private Location getLocation(String[] hexInfo) {
        int x = Integer.parseInt(hexInfo[0]);
        int y = Integer.parseInt(hexInfo[1]);
        int z = Integer.parseInt(hexInfo[2]);
        return new Location(x, y, z);
    }

    private List<Object> getLevel(String[] hexInfo) {
        List<Object> levelAndTerrainInfo = new ArrayList();

        //hex level
        levelAndTerrainInfo.add(new Integer(Integer.parseInt(hexInfo[3])));
        //tile id
        levelAndTerrainInfo.add(new Integer(Integer.parseInt(hexInfo[4])));

        levelAndTerrainInfo.add(getTerrain(hexInfo[5]));

        levelAndTerrainInfo.add(getPiece(hexInfo[6]));

        levelAndTerrainInfo.add(players.get(Integer.parseInt(hexInfo[7])));

        return levelAndTerrainInfo;
    }

    private Terrain getTerrain(String terrain) {
        switch (terrain) {
            case "rocky":
                return Rocky.getInstance();
            case "jungle":
                return Jungle.getInstance();
            case "volcano":
                return Volcano.getInstance();
            case "grassland":
                return Grassland.getInstance();
            case "lake":
                return Lake.getInstance();
            default:
                return null;
        }
    }

    private Piece getPiece(String piece) {
        switch (piece) {

            case "totoro":
                return new Totoro();
            case "tiger":
                return new Tiger();
            case "villager":
                return new Villager();
            case "null":
                return new NullPiece();
            default:
                return new NullPiece();
        }


    }



}
