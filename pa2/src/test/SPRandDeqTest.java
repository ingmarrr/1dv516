
import core.SPRandDeq;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Arrays;

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
    Test.throwAssertQuiet("Size == 10", queue.size() == 10);
    for (int j : range(4)) {
      queue.dequeue();
    }
    Test.throwAssertQuiet("Size == 6", queue.size() == 6);

    var res = queue.dequeue();
    Test.throwAssertQuiet("Result == " + res.get(), res.isPresent());
    Test.throwAssertQuiet("Size == 5", queue.size() == 5);
//    log.info(Arrays.toString(queue.getElems()));
    for (String s : queue) {
//      log.info("i == ", s);
    }

  }
}
