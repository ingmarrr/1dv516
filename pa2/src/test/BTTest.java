import core.BTNode;
import core.BTree;
import logging.Logger;
import logging.Mode;
import testing.Unit;

public class BTTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testIso() {
    var bt = new BTree<Integer>();
    bt.add(1);
    bt.add(2);
    bt.add(3);
    bt.add(4);
    bt.add(5);
    bt.add(6);
    bt.add(7);
    bt.printInorder();

    var other = new BTree<Integer>();
    bt.add(1);
    bt.add(3);
    bt.add(2);
    bt.add(7);
    bt.add(6);
    bt.add(5);
    bt.add(4);

    log.info(bt.isIso(bt.getRoot(), other.getRoot()));
  }
}
