import logging.Logger;
import logging.Mode;
import sorting.Insert;
import testing.Test;
import testing.Unit;

import static range.Range.range;

public class InsertSortTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testInsert() throws Test.FailException {
    var list = new Integer[]{1, 3, 2, 4};
    var sort = new Integer[]{1, 2, 3, 4};
    Insert.sort(list);
    for (int i : range(list.length)) {
      Test.throwAssertQuiet(list[i] + " == " + sort[i], list[i] == sort[i]);
    }
  }
}
