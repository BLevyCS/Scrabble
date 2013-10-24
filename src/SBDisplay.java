
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.util.*;

public class SBDisplay extends JPanel
{
//static final vars for num rows/cols, cell size and Square colors
   private static final int CELL_SIZE = 46;
   private static final int NUM_ROWS = 15;
   private static final int NUM_COLS = 15;

   private Board theBrd;
   private boolean toolTipsEnabled;
   ControlPanel playerControlPanel;
   PointPanel pointsPanel;


   /** Construct a new CBDisplay object for the given board
    * and adds an action listener to it for mouse clicks 
    * and mouse hovering
    * 
    * (TakenPieces takenPiecesPanel, PointPanel pointsPanel)
    */
   public SBDisplay(Board brd, ControlPanel playerControlPanel, PointPanel pointsPanel)
   {
       setToolTipsEnabled(true);
       theBrd = brd;
       this.playerControlPanel = playerControlPanel;
       this.pointsPanel = pointsPanel;
       addMouseListener(new MouseAdapter() {
           public void mouseEntered(MouseEvent evt)
           {
               getToolTipText(evt);
           }
           public void mousePressed(MouseEvent evt)
           {
               selectActionForEvent(evt);
           }
       });
   }

   /** Construct a new SBDisplay object with no board.
    *  The view will be empty.
    **/
   public SBDisplay()
   {
       this(null, null, null);
   }
   //This method forces the repaint request to the front of the system queue 
   //inorder to make this panel instantly repaint
   public void showBrd()
   {
       paintImmediately(0,0,
       this.getWidth(),
       this.getHeight());
   }
   //sets a new board object
   public void setBoard(Board newBrd)
   {
       theBrd = newBrd;
       showBrd();
   }

   public void paint(Graphics g)
   {
       playerControlPanel.paint(g);
       Graphics2D g2 = (Graphics2D)g;
       super.paint(g2);
       if (theBrd == null) return;
       //paint colored squares
       for(int row = 0; row < NUM_ROWS; row++)
       {
           for(int col = 0; col < NUM_COLS; col++)
           {
               g2.setColor(theBrd.spaceAt(row,col).color());
               g2.fillRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
           }
       }
       g2.setColor(Color.black);
       //paint horizontal lines
       for(int y = 0; y < NUM_ROWS+1; y++)
           g2.drawLine(0 , y*CELL_SIZE, (NUM_COLS)*CELL_SIZE, y*CELL_SIZE);
       //paint vertical lines
       for(int x = 0; x < NUM_COLS+1; x++)
           g2.drawLine(x*CELL_SIZE, 0, x*CELL_SIZE, NUM_ROWS*CELL_SIZE);
       //paint the tiles
       ArrayList<Space> brdObjects = theBrd.boardTiles();
       if(brdObjects != null){
           Iterator iter = brdObjects.iterator();
           Space s;
           while (iter.hasNext())
           {
               s = (Space)iter.next();
               drawLocatable(g2, s.occupant(), s.location());
           }
        }

  }
  
    /**
     * Draw a tile object. Find set the color of the space the tile
     * occupies to white, and then put the number and letter on the
     * tile.
     */
   private void drawLocatable(Graphics2D g2, Tile t, Location loc)
   { 
       g2.setColor(Color.white);
       g2.fillRect(colToXCoord(loc.col())+1, rowToYCoord(loc.row())+1, CELL_SIZE-1, CELL_SIZE-1);
       int numY = (rowToYCoord(loc.row() + 1) - 10);
       int numX = (colToXCoord(loc.col() + 1) - 20);
       g2.setColor(Color.black);
       Font drawFont = new Font("MS Mincho",Font.PLAIN,25);
       g2.setFont(drawFont);
       g2.drawString("" + t.letter(), colToXCoord(loc.col()), rowToYCoord(loc.row()) + 25);
       drawFont = new Font("MS Mincho",Font.PLAIN,15);
       g2.setFont(drawFont);
       g2.drawString("" + t.score(), numX, numY);
   }
   /**
    * Make the space s return to its normal color
    */
   private void eraseLocatable(Graphics2D g2, Space s, Location loc)
   {
       g2.setColor(s.color());
       g2.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE);
   }
   
   //selects appropriate action depending on the location of the mouse click and the previous mouse click
   private void selectActionForEvent(MouseEvent evt)
   {
       Location loc = locationForPoint(evt.getPoint());
       //System.out.println("You clicked the screen at location, " + loc);
       if (loc != null)
       {
           if(theBrd.heldTile() != null) // && !otherLoc.equals(loc)
           {
               if(theBrd.putTile(theBrd.spaceAt(loc), theBrd.heldTile()))
               {
                      theBrd.holdTile(null);
                      setBoard(theBrd);
                      playerControlPanel.setBoard(theBrd);
                      pointsPanel.setBoard(theBrd);
               }
           }
       }
   }



   /** Enables/disables showing of tooltip giving information about
    *  the board object beneath the mouse.
    **/
   public void setToolTipsEnabled(boolean flag)
   {
       if (flag)
           ToolTipManager.sharedInstance().registerComponent(this);
       else
           ToolTipManager.sharedInstance().unregisterComponent(this);
   toolTipsEnabled = flag;
   }


   /** Given a MouseEvent, determine what text to place in the floating tool tip when the
    *  the mouse hovers over this location.  If the mouse is over a valid board cell.
    *  we provide some information about the cell and its contents.  This method is
    *  automatically called on mouse-moved events since we register for tool tips.
    **/
   public String getToolTipText(MouseEvent evt)
   {
       repaint();
       Location loc = locationForPoint(evt.getPoint());
       if (!toolTipsEnabled || loc == null)
           return null;
       Space s = theBrd.spaceAt(loc);
       if (s.occupant() != null && !s.type().equals("normal"))
           return loc + " " + s.occupant().toString() + " " + s.type();
       else if(s.occupant() != null)
            return loc + " " + s.occupant().toString() + "";
       else
           return loc + " is empty " + s.type() + " space";
   }
   //method for converting a mouse point to a point in the chess board coordinate system
   public Location locationForPoint(Point p)
   {
       Location loc = new Location(yCoordToRow(p.y), xCoordToCol(p.x));
       return (theBrd != null && theBrd.isValid(loc)) ? loc : null;
   }

   // private helpers to convert between (x,y) and (row,col)
   private int xCoordToCol(int xCoord)
   {
       return (xCoord - getInsets().left)/CELL_SIZE;
   }
   private int yCoordToRow(int yCoord)
   {
       return (yCoord - getInsets().top)/CELL_SIZE;
   }
   private int colToXCoord(int col)
   {
       return (col)*CELL_SIZE + getInsets().left;
   }
   private int rowToYCoord(int row)
   {
       return (row)*CELL_SIZE + getInsets().top;
   }

}