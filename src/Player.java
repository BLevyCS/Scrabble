import java.util.ArrayList;

public class Player
{
    private ArrayList<Tile> hand;
    private int score;
    private int playerNum;
    private Board playingOn;
    
    public Player()
    {
        playingOn = null;
        score = 0;
        hand = new ArrayList(7);
        playerNum = 0;
    }
    
    public Player(int n, Board play)
    {
        hand = new ArrayList(7);
        playingOn = play;
        playerNum = n;
        score = 0;
        for(int i = 0; i<7;i++)
        {
            hand.add(playingOn.bag().getTile());
        }
     }
     
     public void fillHand()
     {
         int i = hand.size();
         for(int j = 7;i < j;i++)
            hand.add(playingOn.bag().getTile());
     }
     
     public String toString()
     {
         return "Player: " + playerNum + " score: " + score;
     }
     public Board board()
     {
         return playingOn;
     }
     public ArrayList<Tile> hand()
     {
         return hand;
     }
     public int playerNum()
     {
         return playerNum;
     }
     public int score()
     {
         return score;
     }
     public int alterScore(int n)
     {
         score+= n;
         return score;
     }
     public Tile useTile(int i)
     {
        return hand.remove(i);
     }
     public ArrayList<Tile> takeTile(Tile t)
     {
         if(hand.size() < 7)
            hand.add(t);
         return hand;
     }
   
}