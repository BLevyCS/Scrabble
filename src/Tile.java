public class Tile
{
    private String letter;
    private int score;
    
    public Tile()
    {
        letter = " ";
        score = 0;
    }
    public Tile(String s)
    {
        letter = s.substring(0,1).toUpperCase();
        if (letter.equals("A") || letter.equals("E") || letter.equals("I") || letter.equals("L") || letter.equals("N") || letter.equals("O") || letter.equals("R") || letter.equals("S") || letter.equals("T") || letter.equals("U"))
            score = 1;
        else if (letter.equals("D") || letter.equals("G"))
            score = 2;
        else if (letter.equals("B") || letter.equals("C") || letter.equals("M") || letter.equals("P"))
            score = 3;
        else if (letter.equals("F") || letter.equals("H") || letter.equals("W") || letter.equals("Y") || letter.equals("V"))
            score = 4;
        else if (letter.equals("K"))
            score = 5;
        else if (letter.equals("J") || letter.equals("X"))
            score = 8;
        else if (letter.equals("Q") || letter.equals("Z"))
            score = 10;
    }
    
    public String toString()
    {
        return letter + ", " + score;
    }
    public int score()
    {
        return score;
    }
    public String letter()
    {
        return letter;
    }
}