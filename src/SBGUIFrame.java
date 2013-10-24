import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SBGUIFrame extends JFrame
{
    private SBDisplay sbDisplay;
    private ControlPanel playerControlPanel;
    private PointPanel pointsPanel;

    //constructs a new board then constructs all necessary GUI copmponents
    public SBGUIFrame()
    {
        setTitle("Scrabble");
        Board brd = new Board();
        playerControlPanel = new ControlPanel(brd);
        playerControlPanel.setPreferredSize(new Dimension(250,100));
        pointsPanel = new PointPanel(brd);
        sbDisplay = new SBDisplay(brd, playerControlPanel, pointsPanel);
        Container c = getContentPane();
        c.add(sbDisplay,BorderLayout.CENTER);
        c.add(playerControlPanel,BorderLayout.EAST);
        setSize(950,750);
        makeMenus();
        setVisible(true);
        
    }
    //generates drop down menus on this JPanel
    private void makeMenus()
    {
        int menuMask = getToolkit().getMenuShortcutKeyMask();
        JMenuBar mbar = new JMenuBar();
        JMenu menu;
        JMenuItem mItem;
        
        mbar.add(menu = new JMenu("File"));
        
        menu.add(mItem = new JMenuItem("Start new Game..."));
        mItem.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { newGame(); }});
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, menuMask));


        menu.add(mItem = new JMenuItem("Quit"));
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, menuMask));
        mItem.addActionListener( new ActionListener() { 
            public void actionPerformed(ActionEvent e) { System.exit(0); }});
            
        mbar.add(menu = new JMenu("Options"));
        
        menu.add(mItem = new JMenuItem("Show player control pane"));
        //Creates an action listener which will generate a frame for the playerControlPanel when an event is detected
        mItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame playerControlWindow = new JFrame("Player Control Panel"); 
                playerControlWindow.getContentPane().add(playerControlPanel);
                playerControlWindow.setSize(100,100);
                playerControlWindow.setLocation(600, 0);
                playerControlWindow.setResizable(false);
                playerControlWindow.setVisible(true); }});
                        
        menu.add(mItem = new JMenuItem("Show point window"));
        //Creates an action listener which will generate a frame for the PointsPanel when an event is detected
        mItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame pointsWindow = new JFrame("Current Points"); 
                pointsWindow.getContentPane().add(pointsPanel);
                pointsWindow.setSize(250,100);
                pointsWindow.setLocation(0, 600);
                pointsWindow.setResizable(false);
                pointsWindow.setVisible(true); }});
                
        /**(Possible future functionality)       
        * menu.add(mItem = new JMenuItem("Show history"));
        * mItem.addActionListener(new ActionListener() {
        *     public void actionPerformed(ActionEvent e) {
        *         History.display(); }});
        */

        setJMenuBar(mbar);
    }    
    //Creates a new game by reseting the board in all of the GUI component
    public void newGame()
    {
        Board brd = new Board();
        sbDisplay.setBoard(brd);
        playerControlPanel.setBoard(brd);
        //pointsPanel.setBoard(brd);       
        repaint();
    }
        
}
