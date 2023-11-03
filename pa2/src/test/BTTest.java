import core.Problem5.BTree;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

public class BTTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testIso() throws Test.FailException {
    var bt = new BTree<Integer>();
    bt.add(1);
    bt.add(2);
    bt.add(3);

    var other = new BTree<Integer>();
    other.add(1);
    other.add(2);
    other.add(3);

    Test.throwAssertQuiet("Are Isomorphic", bt.isIso(other));

  }
}
