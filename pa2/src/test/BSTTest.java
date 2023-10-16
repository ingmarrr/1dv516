import core.BSTree;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Arrays;
import static range.Range.range;

public class BSTTest {

  final static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();


  @Unit
  public void testContains() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();
    var is = new int[] { 1, 2, 3};
    for (int i : is) {
      bst.add(i);
    }

    Test.throwAssertQuiet("Contains 1", bst.contains(1));
    Test.throwAssertQuiet("Contains 2", bst.contains(2));
  }

  @Unit
  public void testRemove() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();
    var is = new int[] { 1, 2, 3};
    for (int i : is) {
      bst.add(i);
    }
    var in = bst.toInOrder();
    for (int i : range(3)) {
      Test.throwAssertQuiet(is[i] + " == " + in.get(i), is[i] == in.get(i));
    }
    Test.throwAssertQuiet("Size == 3", bst.size() == 3);
    bst.remove(2);
    in = bst.toInOrder();
    is = new int[] { 1, 3};
    for (int i : range(2)) {
      Test.throwAssertQuiet(is[i] + " == " + in.get(i), is[i] == in.get(i));
    }
    Test.throwAssertQuiet("Size == 2", bst.size() == 2);
  }

  @Unit
  public void testHeight() throws Test.FailException {
    BSTree<Integer> bst = new BSTree<>();
    var is = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    for (int i : is) {
      bst.add(i);
    }
    Test.throwAssertQuiet("Height == 10", bst.height() == 9);
    bst = new BSTree<>();
    is = new int[] { 5, 3, 8, 2, 1, 4, 9, 10, 7, 6};
    for (int i : is) {
      bst.add(i);
    }
    Test.throwAssertQuiet("Height == 4", bst.height() == 3);
    bst = new BSTree<>();
    is = new int[] { 2, 3, 1};
    for (int i : is) {
      bst.add(i);
    }
    Test.throwAssertQuiet("Height == 2", bst.height() == 1);
  }

  @Unit
  public void testKth() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();
    var is = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    for (int i : is) {
      bst.add(i);
    }
    Test.throwAssertQuiet("Size == 10", bst.size() == 10);
    var kth = bst.kth(3);
    Test.throwAssertQuiet("Size == 9", bst.size() == 9);
    Test.throwAssertQuiet("Is Optional.of", kth.isPresent());
    Test.throwAssertQuiet("kth == 8", kth.get() == 8);
  }

  @Unit
  public void testBSTInorderList() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();

    bst.add(4);
    bst.add(2);
    bst.add(3);
    bst.add(1);
    bst.add(6);
    bst.add(5);
    bst.add(7);

    Test.throwAssertQuiet("In Order Must be: [1, 2, 3, 4, 5, 6, 7]: ", Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7}).equals(bst.toInOrder()));
  }

  @Unit
  public void testBSTInorderIterator() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();

    bst.add(6);
    bst.add(2);
    bst.add(4);
    bst.add(1);
    bst.add(5);
//    bst.add(3);

  }

  @Unit
  public void testBSTPreorderList() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();

    bst.add(4);
    bst.add(2);
    bst.add(3);
    bst.add(1);
    bst.add(6);
    bst.add(5);
    bst.add(7);

    Test.throwAssertQuiet("In Order Must be: [4, 2, 5, 1, 3, 5, 7]: ", Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7}).equals(bst.toInOrder()));
  }

  @Unit
  public void testBSTPreorderIterator() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();
    int[] ints = {5, 3, 8, 2, 4, 7, 9, 1, 6, 10};
    for (int i : ints) {
      bst.add(i);
    }
    var iter = bst.preOrder();

    int[] expected = {5, 3, 2, 1, 4, 8, 7, 6, 9, 10};
    var pre = bst.toPreOrder();
    for (int i = 0; i < 10; i++) {
      Test.throwAssertQuiet(expected[i] + " == " + pre.get(i), expected[i] == pre.get(i));
    }
  }


  @Unit
  public void testBSTPostorderList() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();

    bst.add(4);
    bst.add(2);
    bst.add(3);
    bst.add(1);
    bst.add(6);
    bst.add(5);
    bst.add(7);

    Test.throwAssertQuiet("In Order Must be: [7, 6, 5, 4, 3, 2, 1]: ", Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7}).equals(bst.toInOrder()));
  }

  @Unit
  public void testBSTPostorderIterator() throws Test.FailException {
    final BSTree<Integer> bst = new BSTree<>();

    bst.add(5);
    bst.add(2);
    bst.add(4);
    bst.add(1);
    bst.add(3);

    var iter = bst.postOrder();
  }
}
