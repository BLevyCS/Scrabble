import java.util.ArrayList;
import java.util.Random;

public class Bag
{
    private ArrayList<Tile> bag;
    public Bag()
    {
        bag = new ArrayList(100);
        for(int i = 1;i<=9;i++){//18 tiles
            bag.add(new Tile("A"));
            bag.add(new Tile("I"));
        }
        for(int i = 1;i<=2;i++){//20 tiles
            bag.add(new Tile("B"));
            bag.add(new Tile("C"));
            bag.add(new Tile("F"));
            bag.add(new Tile("M"));
            bag.add(new Tile("H"));
            bag.add(new Tile("P"));
            bag.add(new Tile("V"));
            bag.add(new Tile("W"));
            bag.add(new Tile("Y"));
            //bag.add(new Tile());
        }
        for(int i = 1;i<=4;i++){//16 tiles
            bag.add(new Tile("D"));
            bag.add(new Tile("L"));
            bag.add(new Tile("S"));
            bag.add(new Tile("U"));
        }
        for(int i = 1;i<=12;i++)//12 tiles
            bag.add(new Tile("E"));
        for(int i = 1;i<=3;i++)//3 tiles
            bag.add(new Tile("G"));
        for(int i = 1;i<=6;i++){//18 tiles
            bag.add(new Tile("N"));
            bag.add(new Tile("R"));
            bag.add(new Tile("T"));
        }
        for(int i = 1;i<=8;i++)//8 tiles
            bag.add(new Tile("O"));
        
        //5 tiles
        bag.add(new Tile("J"));
        bag.add(new Tile("K"));
        bag.add(new Tile("X"));        
        bag.add(new Tile("Z"));
        bag.add(new Tile("Q"));
    }
    
    public Tile getTile()
    {
        Random rand = new Random();
        int choose = rand.nextInt(bag.size());
        Tile pick = bag.get(choose);
        bag.set(choose, bag.get(bag.size() - 1));
        bag.remove(bag.size() - 1);
        return pick;
    }
    public void putTile(Tile t)
    {
        bag.add(t);
    }
}
