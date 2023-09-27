
import linkedlist.SPRandDeq;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import static range.Range.range;
public class SPRandDeqTest {

  private static final Logger log = Logger.builder()
      .mode(Mode.Test)
      .build();

  @Unit
  public void testSPRandEnqueue() throws Test.FailException {
    var queue = new SPRandDeq<String>();
    for (int i : range(10)) {
      queue.enqueue(String.valueOf(i));
    }
    Test.throwAssert("Size == 10", queue.size() == 10);
    for (int j : range(4)) {
      queue.dequeue();
    }
    Test.throwAssert("Size == 6", queue.size() == 6);

    var res = queue.dequeue();
    Test.throwAssert("Result == " + res.get(), res.isPresent());
    Test.throwAssert("Size == 5", queue.size() == 5);
    log.info(queue.getElems());
    for (String s : queue) {
      log.info("i == ", s);
    }

  }
}
