import logging.Logger;
import logging.Mode;
import sorting.Quick;
import testing.Test;
import testing.Unit;

import java.util.Arrays;

public class QuickSortTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testQuickSort() throws Test.FailException {

    int[] arr = new int[] { 4, 6, 1, 3, 2, 5 };

    Quick.sort(arr, 5);

    Test.throwAssertQuiet("1 is first", arr[0] == 1);
    Test.throwAssertQuiet("2 is first", arr[1] == 2);
    Test.throwAssertQuiet("3 is first", arr[2] == 3);
    Test.throwAssertQuiet("4 is first", arr[3] == 4);
    Test.throwAssertQuiet("5 is first", arr[4] == 5);
    Test.throwAssertQuiet("6 is first", arr[5] == 6);
  }

  @Unit
  public void testQuickSortDepth0() throws Test.FailException {

    int[] arr = new int[] { 4, 6, 1, 3, 2, 5 };

    Quick.sort(arr, 0);

    Test.throwAssertQuiet("1 is first", arr[0] == 1);
    Test.throwAssertQuiet("2 is first", arr[1] == 2);
    Test.throwAssertQuiet("3 is first", arr[2] == 3);
    Test.throwAssertQuiet("4 is first", arr[3] == 4);
    Test.throwAssertQuiet("5 is first", arr[4] == 5);
    Test.throwAssertQuiet("6 is first", arr[5] == 6);
  }
}
