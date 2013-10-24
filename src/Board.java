import java.util.*;

public class Board
{
    private Space[][] grid;
    
    private Bag theBag;
    private int numPlayers;
    private Player myTurn;
    private Player[] people;
    private Dictionary theDict;
    private Tile heldTile;
    private ArrayList<Space> myWord;
    private boolean startInitiated = false;
    //private ArrayList<String> createdWords;
    private ArrayList<ArrayList<Space>> createdWords;
    
    public Board()
    {
        heldTile = null;
        grid = new Space[15][15];
        numPlayers = 2;
        people = new Player[numPlayers];
        theDict = new Dictionary();
        theBag = new Bag();
        myWord = new ArrayList<Space>();
        createdWords = new ArrayList<ArrayList<Space>>();
        for(int i = 0; i < numPlayers; i++)
        {
            people[i] = new Player(i, this);
        }
        myTurn = people[0];
        
        for(int r = 0;r < grid.length;r++)
        {
            for(int c = 0; c < grid[r].length;c++)
            {
                if((((c-1 == 0)||(c+1 == 14))&&((r == 5)||(r == 9)))   ||   (((r-1 == 0)||(r+1 == 14))&&((c == 5)||(c == 9)))   ||   (((r==5)||(r==9))&&((c==5)||(c==9))))
                    grid[r][c] = new Space(new Location(r,c), "3xlttr");
                else if(((r == 7) && ((c == 3) || (c == 11)))   ||   ((c == 7)&&((r == 3)||(r == 11)))   ||   (((r==6)||(r==8))&&((c==2)||(c==12)))   ||   (((c==6)||(c==8))&&((r==2)||(r==12)))   ||   (((r==6)||(r==8))&&((c==6)||(c==8)))   ||   (((r==3)||(r==11))&&((c==0)||(c==14)))   ||   (((c==3)||(c==11))&&((r==0)||(r==14))))
                    grid[r][c] = new Space(new Location(r,c), "2xlttr");
                else if(((((0<r)&&(r<5))||((14>r)&&(r>9)))&&(((0<c)&&(c<5))||((14>c)&&(c>9))))&&((r==c)   ||   (((14-r) == c)||((14-c) == r))))
                    grid[r][c] = new Space(new Location(r,c), "2xword");
                else
                    grid[r][c] = new Space(new Location(r,c),"normal");
            }
        }
        grid[0][0] = new Space(new Location(0,0), "3xword");
        grid[0][7] = new Space(new Location(0,7), "3xword");
        grid[0][14] = new Space(new Location(0,14), "3xword");
        grid[7][0] = new Space(new Location(7,0), "3xword");
        grid[7][14] = new Space(new Location(7,14), "3xword");
        grid[14][0] = new Space(new Location(14,0), "3xword");
        grid[14][7] = new Space(new Location(14,7), "3xword");
        grid[14][14] = new Space(new Location(14,7), "3xword");
        grid[7][7] = new Space(new Location(7,7), "start");
    }
    /*
    public Board(Space[][] s, Bag b, Dictionary d, int i, Player[] players)
    {
        grid = s;
        numPlayers = i;
        people = players;
        theDict = d;
        theBag = b;
        myWord = new ArrayList<Space>();
    }
    */
    /**
     * Creates a map where Spaces that have tiles are keys
     * and the Tiles that occupy those spaces are the values
     */
    public ArrayList<Space> boardTiles()
    {
        ArrayList<Space> occupiedSpaces = new ArrayList();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j].occupant() != null)
                    occupiedSpaces.add(grid[i][j]);
            }
        }
        if(!occupiedSpaces.isEmpty())
            return occupiedSpaces;
        else
            return null;
    }
    public Tile heldTile()
    {
        return heldTile;
    }
    public void holdTile(Tile t)
    {
        if(heldTile==null)
            heldTile = t;
        if(t==null)
            heldTile = null;
    }
    public Player myTurn()
    {
        return myTurn;
    }
    public Player hisTurn(Player p)
    {
        myTurn = p;
        return myTurn;
    }
    public Bag bag()
    {
        return theBag;
    }
    public Dictionary dictionary()
    {
        return theDict;
    }
    public Player[] players()
    {
        return people;
    }  
    
    /**
     * Methods for checking spaces on the grid
     * Check for tile, check space
     * Check validity, check empty
     */
    public Tile tileAt(Location l)
    {
        return grid[l.row()][l.col()].occupant();
    }
    public Tile tileAt(int r, int c)
    {
        return grid[r][c].occupant();
    }
    public boolean isValid(Location l)
    {
        return (l.row()>=0 && l.col()>=0 && l.row()<=14 && l.col()<=14);
    }
    public boolean isValid(int r, int c)
    {
        return (r>=0 && c>=0 && r<=14 && c<=14);
    }
    public boolean isEmpty(Location l)
    {
        return isValid(l) && (tileAt(l) == null);
    }
    public boolean isEmpty(int r, int c)
    {
        return isValid(r, c) && (tileAt(r, c) == null);
    }
    public Space spaceAt(Location loc)
    {
        return grid[loc.row()][loc.col()];
    }
    public Space spaceAt(int r, int c)
    {
        return grid[r][c];
    }
    public void takeBack()
    {
        if(myWord.size() > 0)
            myTurn.takeTile(myWord.remove(myWord.size()-1).removeOccupant());
    }
    public void removeAll()
    {
        int i = myWord.size();
        for(int j = 0; j<i;j++){
            takeBack();
        }
    }
    public boolean hasLeftSpace(Space s)
    {
        Location myLoc = s.location();
        Location checkLoc = new Location (myLoc.row(), myLoc.col()-1);
        if (isValid(checkLoc))
            if (spaceAt(checkLoc).occupant() != null)
                return true;
        return false;
    }
    public boolean hasRightSpace(Space s)
    {
        Location myLoc = s.location();
        Location checkLoc = new Location (myLoc.row(), myLoc.col()+1);
        if (isValid(checkLoc))
            if (spaceAt(checkLoc).occupant() != null)
                return true;
        return false;
    }
    public boolean hasUpSpace(Space s)
    {
        Location myLoc = s.location();
        Location checkLoc = new Location (myLoc.row()-1, myLoc.col());
        if (isValid(checkLoc))
            if (spaceAt(checkLoc).occupant() != null)
                return true;
        return false;
    }
    public boolean hasDownSpace(Space s)
    {
        Location myLoc = s.location();
        Location checkLoc = new Location (myLoc.row()+1, myLoc.col());
        if (isValid(checkLoc))
            if (spaceAt(checkLoc).occupant() != null)
                return true;
        return false;
    }    
    
    public Space leftMostSpace(Space s)
    {
        if (hasLeftSpace(s))
            return leftMostSpace(spaceAt(new Location (s.location().row(), s.location().col()-1)));
        return s;
    }
    
    public Space rightMostSpace(Space s)
    {
        if (hasRightSpace(s))
            return rightMostSpace(spaceAt(new Location (s.location().row(), s.location().col()+1)));
        return s;
    }
  
    public Space upMostSpace(Space s)
    {
        if (hasUpSpace(s))
            return upMostSpace(spaceAt(new Location (s.location().row()-1, s.location().col())));
        return s;
    }
    /*
    public Space downMostSpace(Space s)
    {
        if (hasDownSpace(s))
            return downMostSpace(spaceAt(new Location (myLoc.row()+1, myLoc.col())));
        return s;
    }
*/
    
    
    /**
     * Check neighbors of a space
     * @return spaces next to s
     */
    public ArrayList<Space> neighborsOf(Space s)
    {
        Location myLoc = s.location();
        ArrayList<Space> nbrs = new ArrayList();
        if(myLoc.row() != 0 && myLoc.row() != 14 && myLoc.col() != 0 && myLoc.col() != 14){
            nbrs.add(spaceAt(new Location(myLoc.row()-1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row()+1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()-1)));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()+1)));
        }
        else if(myLoc.row() == 0){
            nbrs.add(spaceAt(new Location(myLoc.row()+1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()-1)));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()+1)));
        }
        else if(myLoc.row() == 14){
            nbrs.add(spaceAt(new Location(myLoc.row()-1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()-1)));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()+1)));
        }
        else if(myLoc.col() == 0){
            nbrs.add(spaceAt(new Location(myLoc.row()+1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row()-1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()+1)));
        }
        else if(myLoc.col() == 14){
            nbrs.add(spaceAt(new Location(myLoc.row()-1,myLoc.col())));
            nbrs.add(spaceAt(new Location(myLoc.row(),myLoc.col()-1)));
            nbrs.add(spaceAt(new Location(myLoc.row()+1,myLoc.col())));
        }
        return nbrs;
    }
    
    /**
     * Checks neighbors of space and
     * @returns ArrayList of spaces next to s that have tiles
     */
    public ArrayList<Space> tileNeighbors(Space s)
    {
        ArrayList<Space> nbrs = neighborsOf(s);
        ArrayList<Space> tiles = new ArrayList();
        for(int i = 0; i < nbrs.size(); i++){
            if(nbrs.get(i).occupant() != null)
                tiles.add(nbrs.get(i));
        }
        return tiles;
    }
    
    /**
     * Puts Tile t in Space s
     * @returns false if space has a tile in it or if space has no neighboring spaces with tiles.
     */
    public boolean putTile(Space s, Tile t)
    {
        if (!startInitiated)
            if (!(s.type().equals("start")))
                return false;
            else
                startInitiated = true;
                
        if (s.occupant() == null)
        {
            s.setOccupant(t);
            myWord.add(s);
            return true;
        }
        return false;
    }
    
    private boolean goingHorizontal(ArrayList<Space> lst)
    {
        Space check = leftMostSpace(lst.get(0));
        for (Space s : lst)
            if (check != leftMostSpace(s) || !hasLeftSpace(s) && !hasRightSpace(s))
                return false;
        return true;
    }

    
    private boolean goingVertical(ArrayList<Space> lst)
    {
        Space check = upMostSpace(lst.get(0));
        for (Space s : lst)
            if (check != upMostSpace(s) || !hasUpSpace(s) && !hasDownSpace(s))
                return false;
        return true;
    }
        
        
    
    // Checks if the attempted word violates rules.
    public boolean validateWord()
    {
        if (myWord.isEmpty())
            return false;
        return (goingVertical(myWord) || goingHorizontal(myWord));
    }
    
    public void submitWord()
    {
        if (goingVertical(myWord))
        {
            createdWords.add(new ArrayList<Space>());
            Space s = upMostSpace(myWord.get(0));
            while(s.occupant() != null)
            {
                createdWords.get(createdWords.size()-1).add(s);
                s = spaceAt(new Location (s.location().row()+1, s.location().col()));
            }
            for (Space sp : myWord)
            {
                if (hasLeftSpace(sp) || hasRightSpace(sp))
                {
                    createdWords.add(new ArrayList<Space>());
                    Space sc = leftMostSpace(sp);
                    while (sc.occupant() != null)
                    {
                        createdWords.get(createdWords.size()-1).add(sc);
                        sc = spaceAt(new Location (sc.location().row(), sc.location().col()+1));
                    }
                }
            }
        }
        
        if (goingHorizontal(myWord))
        {
            createdWords.add(new ArrayList<Space>());
            Space s = leftMostSpace(myWord.get(0));
            while(s.occupant() != null)
            {
                createdWords.get(createdWords.size()-1).add(s);
                s = spaceAt(new Location (s.location().row(), s.location().col()+1));
            }
            for (Space sp : myWord)
            {
                if (hasUpSpace(sp) || hasDownSpace(sp))
                {
                    createdWords.add(new ArrayList<Space>());
                    Space sc = upMostSpace(sp);
                    while (sc.occupant() != null)
                    {
                        createdWords.get(createdWords.size()-1).add(sc);
                        sc = spaceAt(new Location (sc.location().row()+1, sc.location().col()));
                    }
                }
            }
        }
        
        /* When created words was an arraylist of strings...
        if (goingHorizontal(myWord))
        {
            createdWords.add("");
            Space s = leftMostSpace(myWord.get(0));
            while(hasRightSpace(s))
            {
                createdWords.get(createdWords.size()-1).concat(s.occupant().letter());
                s = spaceAt(new Location (s.location().row(), s.location().col()+1));
            }
            for (Space sp : myWord)
            {
                if (hasLeftSpace(sp) || hasRightSpace(sp))
                {
                    createdWords.add("");
                    sp = upMostSpace(sp);
                    while (hasDownSpace(sp))
                    {
                        createdWords.get(createdWords.size()-1).concat(sp.occupant().letter());
                        sp = spaceAt(new Location (sp.location().row()+1, sp.location().col()));
                    }
                }
            }
        }
        */
       
       
    }
    
    public int scoreWord()
    {
        int totalScore = 0;
        int letterBonus = 0;
        boolean firstWord = true;
        int doub = 0;
        int trip = 0;
        
        for (ArrayList<Space> a : createdWords)
        {
            String str = new String("");
            
            for (Space s : a)
            {
                str = str + s.occupant().letter();
                totalScore += s.occupant().score();
            }
           
            if (!theDict.isFound(str)){
                removeAll();
                myWord.clear();
                createdWords.clear();
                return 0;    
            }
                
        }
        
        for (Space s : myWord)
        {
            if (s.type().equals("3xword"))
                trip++;
            else if (s.type().equals("2xword"))
                doub++;
            else if (s.type().equals("3xlttr"))
                letterBonus += s.occupant().score()*2;
            else if (s.type().equals("2xlttr"))
                letterBonus += s.occupant().score();
        }
        
        while (doub > 0)
        {
            totalScore *= 2;
            doub--;
        }
        while (trip > 0)
        {
            totalScore *= 3;
            trip--;
        }

        totalScore += letterBonus;
        
        myWord.clear();
        createdWords.clear();
        return totalScore;
    }
    
    public Player player()
    {
        return myTurn;
    }
    
    public void nextPlayer()
    {
        if (myTurn.playerNum() + 1 > numPlayers)
            myTurn = people[0];
        else
            myTurn = people[myTurn.playerNum() + 1];
    }
    
/*  Temporary solution when createdWords was arraylist of strings. Does not give bonuses, and each letter is one point.  
    public int scoreWord()
    {
        int totalScore = 0;
        for (String s : createdWords)
        {
            if (!theDict.isFound(s))
                return 0;
            totalScore += s.length();
        }
        
        return totalScore;
    }
    */
    
/*    //Returns the score of the word if it is valid
    //0 if it is not valid, or intersects another word and makes it invalid
    public int finishWord()
    {
        ArrayList<Space> neighbors;
        for(int i = 0; i < myWord.size(); i++){ //Completes myWord with the crossword tiles
            neighbors = tileNeighbors(myWord.get(i));
            for(int j = 0; j < neighbors.size(); j++){
                if(neighbors.get(j).occupant() != null && neighbors.get(j).straightLine(myWord) && !myWord.contains(neighbors.get(j)))
                    myWord.add(neighbors.get(j));
                else if(neighbors.get(j).occupant() != null && !neighbors.get(j).straightLine(myWord))
                    if(!checkConnectingWord(neighbors.get(j)))
                        return 0;
            }
        }
        
        Location listLoc;
        int lowestRow = 15;
        int lowestCol = 15;
        int lowestRowIndex;
        int lowestColIndex;
        boolean across = false;
        boolean down = false;
        for(int i = 0; i < myWord.size(); i++){//Finds the first space of myWord
            listLoc = myWord.get(i).location();
            if(listLoc.row() < lowestRow){
                lowestRow = listLoc.row();
                lowestRowIndex = i;
                down = true;
                across = false;
            }
            
            if(listLoc.col() < lowestCol){
                lowestCol = listLoc.col();
                lowestColIndex = i;
                down = false;
                across = true;
            }
        }
        
        int score = 0;
        boolean twoXWord = false;
        boolean threeXWord = false;
        String theWord = "";
        Space theSpace;
        Tile theTile;
        if(down){//Traverses the word, figures the score, and returns it if the word is valid     
            for(int i = lowestRow; i < myWord.size();  i++) {
                theSpace = grid[i][lowestCol];
                theTile = theSpace.occupant();
                theWord = theWord + theTile.letter();
                
                if(theSpace.type().equals("2xlttr")) //Letter multipliers
                    score += (theTile.score() * 2);
                else if(theSpace.type().equals("3xlttr"))
                    score += theTile.score();
                else
                    score += theTile.score();
                    
                if(theSpace.type().equals("3xword")){//Word multipliers
                    twoXWord = false;
                    threeXWord = true;
                }
                else if((theSpace.type().equals("2xword")) && !threeXWord)
                    twoXWord = true;
            }
        }
        else if(across){
            for(int i = lowestCol; i < myWord.size();  i++) {
                theSpace = grid[lowestRow][i];
                theTile = theSpace.occupant();
                theWord = theWord + theTile.letter();
                
                if(theSpace.type().equals("2xlttr")) //Letter multipliers
                    score += (theTile.score() * 2);
                else if(theSpace.type().equals("3xlttr"))
                    score += theTile.score();
                else
                    score += theTile.score();
                    
                if(theSpace.type().equals("3xword")){//Word multipliers
                    twoXWord = false;
                    threeXWord = true;
                }
                else if((theSpace.type().equals("2xword")) && !threeXWord)
                    twoXWord = true;
            }
        }
        
        if(theDict.isFound(theWord)){
            if(twoXWord)
                return (score * 2);
            else if(threeXWord)
                return (score * 3);
            else
                return score;
        }
        else{
            myWord.clear();
            return 0;
        }
    }
        */
                
}
