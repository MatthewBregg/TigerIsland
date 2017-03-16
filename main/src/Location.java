import javafx.geometry.Point3D;

/**
 * Created by josh on 3/15/17.
 */
public class Location {

    private Point3D coordinates;

    public Location(int x, int y, int z) {
        coordinates = new Point3D(x,y,z);
    }

    public int getX(){
        return (int) coordinates.getX();
    }

    public int getY(){
        return (int) coordinates.getY();
    }

    public int getZ(){
        return (int) coordinates.getZ();
    }
}
