import java.security.InvalidParameterException;
import java.util.Objects;

public class Orientation 
{
	public final static int EAST=0;
	public final static int NORTHEAST=60;
	public final static int NORTHWEST=120;
	public final static int WEST=180;
	public final static int SOUTHWEST=240;
	public final static int SOUTHEAST=300;

	private int dx;
	private int dy;
	private int dz;
	private int angle;

	public Orientation(int dx, int dy, int dz)
	{
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
		if(-(dx+dy)!=dz){
			throw new InvalidParameterException("dx + dy + dz must equal zero");
		}
		
		calculateAngle(dx,dy);
	}
	
	public Orientation(int dx, int dy)
	{
		this.dx = dx;
		this.dy = dy;
		this.dz = -(dx+dy);
		
		calculateAngle(dx,dy);
	}

	public Orientation(int angle)
	{
		this.dx = dx;
		this.dy = dy;
		this.dz = -(dx+dy);
		
		calculateAngle(dx,dy);
	}
	
	private void calculateAngle(int dx2, int dy2) {
		if(dx==0 && dy ==-1){
			angle = SOUTHEAST;
		}
		if(dx==1 && dy ==-1){
			angle = EAST;
		}
		if(dx==-1 && dy ==0){
			angle = SOUTHWEST;
		}
		if(dx==0 && dy ==1){
			angle = NORTHWEST;
		}
		if(dx==1 && dy ==0){
			angle = NORTHEAST;
		}
		if(dx==-1 && dy ==1){
			angle = WEST;
		}
	}

	public int getDx() 
	{
		return dx;
	}

	public int getDy() 
	{
		return dy;
	}

	public int getDz() 
	{
		return dz;
	}
	
	public int getAngle()
	{
		return angle;
	}

	public static Orientation getEast()
	{
		return new Orientation(EAST);
	}

	public static Orientation getWest()
	{
		return new Orientation(WEST);
	}

	public static Orientation getNorthEast()
	{
		return new Orientation(NORTHEAST);
	}

	public static Orientation getSouthEast()
	{
		return new Orientation(SOUTHEAST);
	}

	public static Orientation getNorthWest()
	{
		return new Orientation(NORTHWEST);
	}

	public static Orientation getSouthWest()
	{
		return new Orientation(SOUTHWEST);
	}
	
	public int hashCode()
	{
		return Objects.hash(dx,dy,dz);
	}
	
	public boolean equals(Object o)
	{
		if(!o.getClass().equals(getClass()))
		{
			return false;
		}
		
		Orientation orient=(Orientation)o;
		
		return orient.getAngle()==this.getAngle();
	}
	
	public String toString()
	{
		switch(angle)
		{
		case EAST:
			return "East";
		case NORTHEAST:
			return "Northeast";
		case NORTHWEST:
			return "Northwest";
		case WEST:
			return "West";
		case SOUTHWEST:
			return "Southwest";
		case SOUTHEAST:
			return "Southeast";
		}
		
		return "Unknown Direction";
	}
}
