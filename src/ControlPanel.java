/*
 * Louis DaPrato
 * 5/25/06
 * Mr.Wiland
 * This class handles the painting of the current taken pieces on to a JPanel
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ControlPanel extends JPanel
{
   private static final int CELL_SIZE = 46;
   private Board theBrd;
   private javax.swing.Timer timer;
   private int firstToPass;
   
   public ControlPanel(Board brd)
   {
       firstToPass = -1;
       theBrd = brd;
       addMouseListener(new MouseAdapter() {
           public void mousePressed(MouseEvent evt)
           {
               selectActionForEvent(evt);
           }
       });
   }
   //set a new board
   public void setBoard(Board brd)
   {
       theBrd = brd;
       repaint();
   }
    
   public void paint(Graphics g)
   {
       super.paint(g);
       Graphics2D g2d = (Graphics2D)g;
       //these vars remember how many of each pieces has been displayed in order to adjust the coordinate system accordingly
       Player myTurn = theBrd.myTurn();
       int tilesInHand = 0;
       g2d.setColor(Color.black);
       Font drawFont = new Font("MS Mincho",Font.PLAIN,25);
       g2d.setFont(drawFont);
       g2d.drawString(theBrd.myTurn().toString(),4,20);
       //get all currently taken pieces and then diplay them
       ArrayList<Tile> myHand = myTurn.hand();
       Location loc = null;
       for (int i = 0; i < 7; i++)
       {
           loc = new Location(tilesInHand+1,2);
           tilesInHand++;
           if(i<myHand.size()){
               Tile t = myHand.get(i);
                drawLocatable(g2d, t, loc);
            }
           else
                eraseLocatable(g2d, loc);
       }   
       loc = new Location(tilesInHand+1,2);
       g2d.setColor(Color.blue);
       g2d.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE - 1);
       g2d.setColor(Color.yellow);
       g2d.drawString("Done", colToXCoord(loc.col()) +5, rowToYCoord(loc.row()) +25);
       loc = new Location(tilesInHand+2,2);
       g2d.setColor(Color.blue);
       g2d.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE - 1);
       g2d.setColor(Color.yellow);
       g2d.drawString("Undo", colToXCoord(loc.col()) +5, rowToYCoord(loc.row()) +25);
       loc = new Location(tilesInHand+3,2);
       g2d.setColor(Color.blue);
       g2d.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE - 1);
       g2d.setColor(Color.yellow);
       g2d.drawString("Pass", colToXCoord(loc.col()) +5, rowToYCoord(loc.row()) +25);
    }
    
    /**
     * Draw a tile object. Find set the color of the space the tile
     * occupies to white, and then put the number and letter on the
     * tile.
     */
   private void drawLocatable(Graphics2D g2, Tile t, Location loc)
   { 
       g2.setColor(Color.green);
       g2.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE - 1);
       int numY = (rowToYCoord(loc.row() + 1) - 10);
       int numX = (colToXCoord(loc.col() + 1) - 20);
       g2.setColor(Color.black);
       Font drawFont = new Font("MS Mincho",Font.PLAIN,25);
       g2.setFont(drawFont);
       g2.drawString("" + t.letter(), colToXCoord(loc.col()), rowToYCoord(loc.row()) + 20);
       drawFont = new Font("MS Mincho",Font.PLAIN,15);
       g2.setFont(drawFont);
       g2.drawString("" + t.score(), numX, numY);
   }
   /**
    * Make the space s return to its normal color
    */
   private void eraseLocatable(Graphics2D g2, Location loc)
   {
       g2.setColor(Color.white);
       g2.fillRect(colToXCoord(loc.col()), rowToYCoord(loc.row()), CELL_SIZE, CELL_SIZE);
   }
   
   private void selectActionForEvent(MouseEvent evt)
   {
       Location loc = locationForPoint(evt.getPoint());
       //System.out.println("You clicked the screen at location, " + loc);
       if (loc != null)
       {
           int theTile = loc.row() - 1;
           if(theTile<7){
               if(theBrd.myTurn().hand().size() > 0 && loc.col() == 2){
                   if(theBrd.heldTile() == null)
                   {
                       Tile t = theBrd.myTurn().useTile(theTile);
                       theBrd.holdTile(t);
                       repaint();
                    }    
                    else
                    {
                        Tile t = theBrd.heldTile();
                        theBrd.myTurn().takeTile(t);
                        theBrd.holdTile(null);
                        repaint();
                    }
                }
                else if(theBrd.myTurn().hand().size() > 0 && theBrd.heldTile() != null){
                    Tile t = theBrd.heldTile();
                    theBrd.myTurn().takeTile(t);
                    theBrd.holdTile(null);
                    repaint();
                }
            }
            else if(theTile==7){
                firstToPass = -1;
                int myTurnNum = theBrd.myTurn().playerNum();
                int nextTurnNum = (myTurnNum + 1)%2;
                Player[] plyrs = theBrd.players();
                Player nextPlayer = plyrs[nextTurnNum];
                if(theBrd.validateWord()){
                    theBrd.submitWord();
                    int scr = theBrd.scoreWord();
                    if(scr > 0){                   
                        theBrd.myTurn().alterScore(scr);
                        theBrd.myTurn().fillHand();
                        theBrd.hisTurn(nextPlayer);
                    }
                    repaint();
                }
                else
                    theBrd.removeAll();
                repaint();
            }
            else if(theTile==8){
                theBrd.takeBack();
                repaint();
            }
            else if(theTile==9){
                theBrd.removeAll();
                int myTurnNum = theBrd.myTurn().playerNum();
                int nextTurnNum = (myTurnNum + 1)%2;
                Player[] plyrs = theBrd.players();
                Player nextPlayer = plyrs[nextTurnNum];
                if(firstToPass < 0){
                    firstToPass = theBrd.myTurn().playerNum();
                    theBrd.hisTurn(nextPlayer);
                }
                else if(firstToPass == theBrd.myTurn().playerNum()){
                    //end game
                }
                else
                    theBrd.hisTurn(nextPlayer);
                repaint();
            }
                
       }
   }
   
   public Location locationForPoint(Point p)
   {
       Location loc = new Location(yCoordToRow(p.y), xCoordToCol(p.x));
       return (theBrd != null && theBrd.isValid(loc)) ? loc : null;
   }
   
    //private methods for (row,col) to (x,y) converting
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
