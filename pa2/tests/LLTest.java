import linkedlist.DLList;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Objects;
import java.util.Optional;

public class LLTest {
  final Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testLL() throws Test.FailException {
    var ll = new DLList<Integer>();
    ll.addFirst(1);
    Test.throwAssert("[1]", Objects.equals(ll.remFirst(), Optional.of(1)));
  }
}
