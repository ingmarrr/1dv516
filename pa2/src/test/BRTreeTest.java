import core.BRNode;
import core.BRTree;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class BRTreeTest {

  private static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testCase0() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(1);
    var node = brt.get(1);
    Test.throwAssertQuiet("Must be black", node.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("Is not red", node.get().getColor() != BRNode.Color.Red);
  }

  @Unit
  public void testOneRotationRight() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(3);
    brt.add(2);
    brt.add(1);
    var one = brt.get(1);
    var two = brt.get(2);
    var three = brt.get(3);
    Test.throwAssertQuiet("2 Must be black", two.isPresent() && two.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("1 Must be red", one.isPresent() && one.get().getColor() == BRNode.Color.Red);
    Test.throwAssertQuiet("3 Must be red", three.isPresent() && three.get().getColor() == BRNode.Color.Red);
  }

  @Unit
  public void testOneRotationLeft() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(1);
    brt.add(2);
    /**
     *   1b
     *    \
     *     2r
     *     +
     *      3r
     */
    brt.add(3);
    /**
     *     2b
     *    /  \
     *  1r    3r
     */
    var one = brt.get(1);
    var two = brt.get(2);
    var three = brt.get(3);
    Test.throwAssertQuiet("2 Must be black", two.isPresent() && two.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("1 Must be red", one.isPresent() && one.get().getColor() == BRNode.Color.Red);
    Test.throwAssertQuiet("3 Must be red", three.isPresent() && three.get().getColor() == BRNode.Color.Red);
  }

  @Unit
  public void testOnlyRecolorUncleParentRoot() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(2);
    brt.add(1);
    brt.add(3);
    /**
     *     2b
     *    /  \
     *  1r    3r
     */
    brt.add(4);
    /** Left Rotation
     *      2b->r->b     - Two Corrections, apply rule U & C == Red
     *     /  \            then rule where root has to be black
     *   1r->b 3r->b     - One correction, apply rule where U & P are red
     *          \
     *           4r
     */
    var one = brt.get(1);
    var two = brt.get(2);
    var three = brt.get(3);
    var four = brt.get(4);
    Test.throwAssertQuiet("2 must be black", two.isPresent() && two.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("1 must be black", one.isPresent() && one.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("3 must be black", three.isPresent() && three.get().getColor() == BRNode.Color.Black);
    Test.throwAssertQuiet("4 must be red", four.isPresent() && four.get().getColor() == BRNode.Color.Red);
  }

  @Unit
  public void testMultipleRotations() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(7);
    brt.add(2);
    brt.add(10);
    /*
     *     7b
     *    /  \
     *  2r    10r
     */
    Test.throwAssertQuiet("7  == Black", brt.get(7).get().getColor().isBlack());
    Test.throwAssertQuiet("2  == Red", brt.get(2).get().getColor().isRed());
    Test.throwAssertQuiet("10 == Red", brt.get(10).get().getColor().isRed());

    brt.add(11);
    /*
     *     7b
     *    /  \
     *  2b    10b
     *         \
     *         11r
     */
    Test.throwAssertQuiet("7  == Black", brt.get(7).get().getColor().isBlack());
    Test.throwAssertQuiet("2  == Black", brt.get(2).get().getColor().isBlack());
    Test.throwAssertQuiet("10 == Black", brt.get(10).get().getColor().isBlack());
    Test.throwAssertQuiet("11 == Red", brt.get(11).get().getColor().isRed());

    brt.add(12);
    /*
     *     7b
     *    /  \
     *  2b    11b
     *       / \
     *     10r  12r
     */

    Test.throwAssertQuiet("7  == Black", brt.get(7).get().getColor().isBlack());
    Test.throwAssertQuiet("2  == Black", brt.get(2).get().getColor().isBlack());
    Test.throwAssertQuiet("10 == Red", brt.get(10).get().getColor().isRed());
    Test.throwAssertQuiet("11 == Black", brt.get(11).get().getColor().isBlack());
    Test.throwAssertQuiet("12 == Red", brt.get(12).get().getColor().isRed());

    try {
      brt.add(8);
    } catch (Exception e) {
      e.printStackTrace();
    }
//    brt.add(9);
    /*
     *     7b
     *    /  \
     *  2b    11r
     *       / \
     *     10b  12b
     *    +  \
     *   8r   9r
     */

//    brt.add(6);
//    brt.add(1);
//    /*
//     *       7b
//     *      /  \
//     *    2b    11r
//     *   / \    /  \
//     *  1r  6r 10b  12b
//     *         /     \
//     *        8r      9r
//     */
//    Test.throwAssertQuiet("7  == Black", brt.get(7).get().getColor().isBlack());
//    Test.throwAssertQuiet("2  == Black", brt.get(2).get().getColor().isBlack());
//    Test.throwAssertQuiet("10 == Black", brt.get(10).get().getColor().isBlack());
//    Test.throwAssertQuiet("11 == Red", brt.get(11).get().getColor().isRed());
//    Test.throwAssertQuiet("12 == Black", brt.get(12).get().getColor().isBlack());
//    Test.throwAssertQuiet("8 == Red", brt.get(8).get().getColor().isRed());
//    Test.throwAssertQuiet("9 == Red", brt.get(9).get().getColor().isRed());
//    Test.throwAssertQuiet("6 == Red", brt.get(6).get().getColor().isRed());
//    Test.throwAssertQuiet("1 == Red", brt.get(1).get().getColor().isRed());

  }

  @Unit
  public void testDeleteRedLeaf() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(3);
    brt.add(2);

    var res = brt.del(2);
    Test.throwAssertQuiet("Is Some", res.isEmpty());
    var noTwo = brt.get(2);
    Test.throwAssertQuiet("No Two", noTwo.isEmpty());
  }

  @Unit
  public void testDeleteRedOneChild() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(3);
    brt.add(2);
    brt.add(1);
    brt.add(4);
    /*
     *    2b
     *   / \
     *  1b  3b
     *       \
     *       4r
     */

    brt.del(3);
    /*
     *    2b
     *   /  \
     *  1b   4b
     */
    brt.del(2);
    /*
     *  1b
     *    \
     *     4r
     */
    var one = brt.get(1);
    var four = brt.get(4);
    Test.throwAssertQuiet("One is Root", one.isPresent() && one.get().parent.isEmpty());
    Test.throwAssertQuiet("Four is Red", four.isPresent() && four.get().getColor().isRed());
  }

  @Unit
  public void testDeleteBlackSiblingTwoRedChildren() throws Test.FailException {
    var brt = new BRTree<Integer>();
    brt.add(2);
    brt.add(1);
    brt.add(3);
    brt.add(4);
    brt.add(5);
    /*
     *     2b
     *    / \
     *   1b  4b
     *      /  \
     *     3r   5r
     */

    brt.del(1);
     /*
      *    3b
      *   /  \
      *  2b  4b
      *        \
      *         5r
     */
    var one = brt.get(1);
    var two = brt.get(2);
    var three = brt.get(3);
    var four = brt.get(4);
    var five = brt.get(5);
    Test.throwAssertQuiet("One must be None", one.isEmpty());
    Test.throwAssertQuiet("Two must be Some and Black", two.isPresent() && two.get().getColor().isBlack());
    Test.throwAssertQuiet("Three must be Some and Black", three.isPresent() && three.get().getColor().isBlack());
    Test.throwAssertQuiet("Four must be Some and Black", four.isPresent() && four.get().getColor().isBlack());
    Test.throwAssertQuiet("Five must be Some and Red", five.isPresent() && five.get().getColor().isRed());

    brt.del(2);
    /*
     *    3b
     *   /  \
     *  2b   4b
     */
    two = brt.get(2);
    three = brt.get(3);
    four = brt.get(4);
    five = brt.get(5);
  }

}
