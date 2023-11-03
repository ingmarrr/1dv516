import core.Problem6.AVLTree;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

public class AVLTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testALVInsert() throws Test.FailException {
    AVLTree<Integer> at = new AVLTree<>();
    at.add(1);
    at.add(2);
    at.add(3);
    /*
     *    2
     *   / \
     *  1   3
     */
    at.add(4);
    /*
     *    2
     *   / \
     *  1   3
     *       \
     *        4
     */
    at.add(5);
    /* Right-Right Case -> Left Rotation on 3
     *    2
     *   / \
     *  1   4
     *     / \
     *    3   5
     */
    at.add(7);
    /* Right-Right Case -> Left Rotation on 2
     *       4
     *     /   \
     *    2     5
     *   / \     \
     *  1   3     7
     *
     */
    at.add(6);
    /* Right-Left Case -> Double Rotation, First Right Rotation on 7 then Left Rotation on 5
     *       4
     *     /   \
     *    2     6
     *   /     / \
     *  1     5   7
     *       /
     *      4
     */

//      at.printLvl();
    /* 4
     * │  ├──2
     * │  │  ├──1
     * │  │  └──3
     * │  └──6
     * │     └──5
     * │     └──7
     */
    var root = at.getRoot();
    Test.throwAssertQuiet("Root == 4", root.isPresent() && root.get().getVal() == 4);
  }
}
