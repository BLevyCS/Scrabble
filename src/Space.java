import java.awt.Color;
import java.util.*;

public class Space
{
    private String type;
    private Tile occupant;
    private Location loc;
    private Color color;
    
    public Space(Location location, String s)
    {
        occupant = null;
        type = s.toLowerCase();
        if (type.equals("3xword"))
            color = new Color(250,0,0);
        else if(type.equals("2xword"))
            color = new Color(199,21,133);
        else if(type.equals("3xlttr"))
            color = new Color(0,0,255);
        else if(type.equals("2xlttr"))
            color = new Color(135,206,235);
        else if(type.equals("start"))
            color = new Color(0,250,0);
        else
            color = new Color(255,130,71);   
        loc = location;
    }
    
    public String toString()
    {
        if (occupant != null)
            return "(" + loc.row() + "," + loc.col() + ") " + type + " " + occupant.toString();
         else
            return "(" + loc.row() + "," + loc.col() + ") " + type + " empty";
    }
    public String type()
    {
        return type;
    } 
    public Tile occupant()
    {
        return occupant;
    }  
    public Location location()
    {
        return loc;
    }
    public Color color()
    {
        return color;
    }
    public Tile setOccupant(Tile t)
    {
        occupant = t;
        return occupant;
    }
    /*
    public boolean straightLine(ArrayList<Space> l)
    {
        l.add(this);
        Location myLoc = l.get(0).location();
        Location nextLoc = l.get(1).location();
        if(myLoc.row() == nextLoc.row()){
            for(int i = 1; i < l.size() - 1 ; i++) {
                l.get(i).location();
                l.get(i+1).location();
                if(myLoc.row() != nextLoc.row())
                    return false;
            }
        }
        else if (myLoc.col() == nextLoc.col()) {
            for(int i = 1; i < l.size() - 1 ; i++) {
                l.get(i).location();
                l.get(i+1).location();
                if(myLoc.col() != nextLoc.col())
                    return false;
            }
        }
        else
            return false;
        return true;
    }
    */
   
    public Tile removeOccupant()
    {
        Tile t = occupant;
        occupant = null;
        return t;
    }
   
    public int compareTo(Space other)
    {
        return loc.compareTo(other.location());
    }
       
}
