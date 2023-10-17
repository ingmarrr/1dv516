import logging.Logger;
import logging.Mode;
import sorting.Heap;
import testing.Test;
import testing.Unit;

import java.util.List;

public class HeapSortTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testHeapSort() throws Test.FailException {
    final List<Integer> list = List.of(1, 3, 2, 5, 4);
    final Heap<Integer> hp = new Heap<Integer>(2, list);
    hp.sort();
  }
}
