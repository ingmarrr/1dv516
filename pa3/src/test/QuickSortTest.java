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

  public static Quick.Fallback fallback = Quick.Fallback.Heap;

  @Unit
  public void testQuickSort() throws Test.FailException {

    int[] arr = new int[] { 4, 6, 1, 3, 2, 5 };

    Quick.sort(arr, 5, fallback);

    Test.throwAssert("1 is first", arr[0] == 1);
    Test.throwAssert("2 is first", arr[1] == 2);
    Test.throwAssert("3 is first", arr[2] == 3);
    Test.throwAssert("4 is first", arr[3] == 4);
    Test.throwAssert("5 is first", arr[4] == 5);
    Test.throwAssert("6 is first", arr[5] == 6);
  }

  @Unit
  public void testQuickSortDepth0() throws Test.FailException {

    int[] arr = new int[] { 4, 6, 1, 3, 2, 5 };

    try {
      Quick.sort(arr, 1, fallback);
    } catch (Exception E) {
      E.printStackTrace();
    }
    System.out.println(Arrays.toString(arr));

    Test.throwAssert("1 is first", arr[0] == 1);
    Test.throwAssert("2 is second", arr[1] == 2);
    Test.throwAssert("3 is third", arr[2] == 3);
    Test.throwAssert("4 is fourth", arr[3] == 4);
    Test.throwAssert("5 is fifth", arr[4] == 5);
    Test.throwAssert("6 is sixth", arr[5] == 6);
  }

  @Unit
  public void testQuickSortDuplicates() throws Test.FailException {
    int[] arr = new int[] { 10, 10, 20, 30, 10, 10, 50, 10, 30, 20, 10 };

    Quick.sort(arr, 0, fallback);
    log.info(Arrays.toString(arr));
  }
}
