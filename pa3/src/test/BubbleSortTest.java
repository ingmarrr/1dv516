import logging.Logger;
import logging.Mode;
import sorting.Bubble;
import testing.Test;
import testing.Unit;

import java.util.Arrays;
import java.util.List;

import static range.Range.range;

public class BubbleSortTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testBubbleSort() throws Test.FailException {
    var list = new Integer[] { 1, 3, 5, 2, 4 };
    var sort = new Integer[] { 1, 2, 3, 4, 5 };
    Bubble.sort(list);
    for (int i : range(list.length)) {
      Test.throwAssertQuiet(list[i] + " == " + sort[i], list[i] == sort[i]);
    }
  }
}
