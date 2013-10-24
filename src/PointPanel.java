/*
 * Louis DaPrato
 * 5/25/06
 * Mr.Wiland
 * This class handles the painting of the current points for white and black
 */
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class PointPanel extends JPanel
{
    Board theBrd;
    private javax.swing.Timer timer;
    private static final int CELL_SIZE = 20;
    
    public PointPanel(Board brd)
    {
        theBrd = brd;
    }
    //sets the board
    public void setBoard(Board brd)
    {
        theBrd = brd;
        repaint();
    }
  
    public void paint(Graphics g)
    {
        super.paint(g);
        //adjust font for text to be painted
        g.setColor(Color.black);
        Font drawFont = new Font("MS Mincho",Font.PLAIN,10);
        g.setFont(drawFont);
        //paint text
        g.drawString("Player0 Points", CELL_SIZE, 15);
        g.drawString("Player1 Points", CELL_SIZE*7, 15);
        //get currently taken pieces then add up points
        int blackPoints = theBrd.players()[0].score();
        int whitePoints = theBrd.players()[1].score();
        //print points
        g.drawString("" + blackPoints, 3*CELL_SIZE-2, 28);
        g.drawString("" + whitePoints, 9*CELL_SIZE-2, 28);
        
    }
    


}
