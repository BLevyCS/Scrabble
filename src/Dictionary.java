import java.util.Arrays;

public class Dictionary
{
  private DictA dictA;
  private DictB dictB;
  private DictC dictC;
  private DictDE dictDE;
  private DictFG dictFG;
  private DictHI dictHI;
  private DictJKL dictJKL;
  private DictM dictM;
  private DictNO dictNO;
  private DictP dictP;
  private DictQR dictQR;
  private DictS dictS;
  private DictSm dictSm;
  private DictT dictT;
  private DictUV dictUV;
  private DictWXYZ dictWXYZ;
  
  public Dictionary()
  {
    dictA = new DictA();
    dictB = new DictB();
    dictC = new DictC();
    dictDE = new DictDE();
    dictFG = new DictFG();
    dictHI = new DictHI();
    dictJKL = new DictJKL();
    dictM = new DictM();
    dictNO = new DictNO();
    dictP = new DictP();
    dictQR = new DictQR();
    dictS = new DictS();
    dictSm = new DictSm();
    dictT = new DictT();
    dictUV = new DictUV();
    dictWXYZ = new DictWXYZ();
  }

  public boolean isFound(String target)
  {
      target = target.toLowerCase();
      if (target.substring(0,1).equals("a"))
        return dictA.isFound(target);
      else if (target.substring(0,1).equals("b"))
        return dictB.isFound(target);
      else if (target.substring(0,1).equals("c"))
        return dictC.isFound(target);
      else if (target.substring(0,1).equals("d") || target.substring(0,1).equals("e"))
        return dictDE.isFound(target);
      else if (target.substring(0,1).equals("f") || target.substring(0,1).equals("g"))
        return dictFG.isFound(target);
      else if (target.substring(0,1).equals("h") || target.substring(0,1).equals("i"))
        return dictHI.isFound(target);
      else if (target.substring(0,1).equals("j") || target.substring(0,1).equals("k") || target.substring(0,1).equals("k"))
        return dictJKL.isFound(target);
      else if (target.substring(0,1).equals("m"))
        return dictM.isFound(target);
      else if (target.substring(0,1).equals("n") || target.substring(0,1).equals("o"))
        return dictNO.isFound(target);
      else if (target.substring(0,1).equals("p"))
        return dictP.isFound(target);
      else if (target.substring(0,1).equals("q") || target.substring(0,1).equals("r"))
        return dictQR.isFound(target);
      else if (target.substring(0,1).equals("s") && target.substring(0,2).compareTo("sm") < 0)
        return dictS.isFound(target);
      else if (target.substring(0,1).equals("s") && target.substring(0,2).compareTo("sm") >= 0)
        return dictSm.isFound(target);
      else if (target.substring(0,1).equals("t"))
        return dictT.isFound(target);
      else if (target.substring(0,1).equals("u") || target.substring(0,1).equals("v"))
        return dictUV.isFound(target);
      else if (target.substring(0,1).equals("w") || target.substring(0,1).equals("x") || target.substring(0,1).equals("y") || target.substring(0,1).equals("z"))
        return dictWXYZ.isFound(target);
      return false;
        
  }
}

