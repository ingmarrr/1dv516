import logging.Logger;
import logging.Mode;
import sorting.Insert;
import sorting.Quick;
import testing.Test;
import testing.Unit;

import java.util.Arrays;

import static range.Range.range;

public class InsertSortTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testInsertSort() throws Test.FailException {
    var list = new Integer[]{1, 3, 2, 4};
    var sort = new Integer[]{1, 2, 3, 4};
    Insert.sort(list);
    for (int i : range(list.length)) {
      Test.throwAssertQuiet(list[i] + " == " + sort[i], list[i] == sort[i]);
    }
  }

  @Unit
  public void testInsertSort2() throws Test.FailException {

    int[] arr = new int[] { 4, 6, 1, 3, 2, 5 };

    Insert.sort(arr);

    Test.throwAssertQuiet("1 is first", arr[0] == 1);
    Test.throwAssertQuiet("2 is first", arr[1] == 2);
    Test.throwAssertQuiet("3 is first", arr[2] == 3);
    Test.throwAssertQuiet("4 is first", arr[3] == 4);
    Test.throwAssertQuiet("5 is first", arr[4] == 5);
    Test.throwAssertQuiet("6 is first", arr[5] == 6);


  }
}
