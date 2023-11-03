import core.Problem3.MinHeap;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

public class MinHeapTest {

  public static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testMinHeap() throws Test.FailException {
    var arr = new Integer[]{3, 1, 5, 4, 2};
    var heap = new MinHeap<>(2, Integer.class, arr);
    for (var i : arr) heap.push(i);
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    heap.push(4);
//    heap.push(1);
//    heap.push(2);
//    log.info(heap.popMin());
//    log.info(heap.popMin());
//    log.info(heap.popMin());
  }
}
