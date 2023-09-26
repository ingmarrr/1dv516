package test;

import linkedlist.DLList;
import logging.Logger;
import logging.Mode;
import testing.Unit;
import testing.Test;

public class LLTest {
  private final Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testLL() throws Test.FailException {
    var ll = new DLList<Integer>();
    ll.addFirst(1);
    Test.throwAssert("First Elem == 1", ll.remFirst().get() == 2);
  }

  @Unit
  public void testDLLIterator() throws Test.FailException {
    var ll = new DLList<Integer>();
    ll.addLast(1);
    ll.addLast(2);
    ll.addLast(3);
    ll.addLast(4);
    Test.throwAssert("Size == 4", ll.size() == 4);
  }
}
