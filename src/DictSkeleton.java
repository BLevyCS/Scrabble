import java.util.Arrays;

public class DictSkeleton
{
  private String[] words =
  {
      
  };

  public boolean isFound(String target)
  {
    return Arrays.binarySearch(words, target) >= 0;
  }
}
