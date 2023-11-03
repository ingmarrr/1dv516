
import core.Problem2.SPRandDeque;
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
    var queue = new SPRandDeque<String>();
    for (int i : range(10)) {
      queue.enqueue(String.valueOf(i));
    }

    // Even tho we run the iterator, we dont consume the queue
    // This was an identified problem in my previous submission,
    // now fixed
    for (String i : queue) {
//      log.debug(i);
    }

    Test.throwAssertQuiet("Size == 10", queue.size() == 10);
    for (int j : range(4)) {
      queue.dequeue();
    }
    Test.throwAssertQuiet("Size == 6", queue.size() == 6);

    var res = queue.dequeue();
    Test.throwAssertQuiet("Result == " + res.get(), res.isPresent());
    Test.throwAssertQuiet("Size == 5", queue.size() == 5);

  }
}
