import core.BST;
import logging.Logger;
import logging.Mode;
import testing.Test;
import testing.Unit;

import java.util.Arrays;
import java.util.Iterator;

public class BSTTest {

  final static Logger log = Logger.builder()
      .mode(Mode.Test)
      .emoji(true)
      .build();

  @Unit
  public void testKth() throws Test.FailException {
    final BST<Integer> bst = new BST<>();
    var is = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    for (int i : is) {
      bst.add(i);
    }
    Test.throwAssertQuiet("Size == 10", bst.getSize() == 10);
    var kth = bst.kth(3);
    Test.throwAssertQuiet("Size == 9", bst.getSize() == 9);
    Test.throwAssertQuiet("Is Optional.of", kth.isPresent());
    Test.throwAssertQuiet("kth == 8", kth.get() == 8);
  }

  @Unit
  public void testBSTInorderList() throws Test.FailException {
    final BST<Integer> bst = new BST<>();

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
    final BST<Integer> bst = new BST<>();

    bst.add(6);
    bst.add(2);
    bst.add(4);
    bst.add(1);
    bst.add(5);
//    bst.add(3);

  }

  @Unit
  public void testBSTPreorderList() throws Test.FailException {
    final BST<Integer> bst = new BST<>();

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
    final BST<Integer> bst = new BST<>();
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
    final BST<Integer> bst = new BST<>();

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
    final BST<Integer> bst = new BST<>();

    bst.add(5);
    bst.add(2);
    bst.add(4);
    bst.add(1);
    bst.add(3);

    var iter = bst.postOrder();
  }
}
