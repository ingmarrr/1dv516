package core;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class LCRSNode<E extends Comparable<E>> {
  private final E val;
  private Optional<LCRSNode<E>> left;
  private Optional<LCRSNode<E>> right;

  public E getVal() {
    return val;
  }

  public LCRSNode(E value) {
    left = empty();
    right = empty();
    val = value;
  }

  public void print() {
    System.out.println(val);
    left.ifPresent(LCRSNode::print);
    right.ifPresent(LCRSNode::print);
  }

  public int degree() {
    int deg = 0;
    Optional<LCRSNode<E>> child = left;
    while (child.isPresent()) {
      child = child.get().right;
      deg++;
    }
    return deg;
  }

  public LCRSNode<E> add(E val) {
    if (left.isEmpty()) {
      final LCRSNode<E> newLeft = new LCRSNode<>(val);
      left = of(newLeft);
      return newLeft;
    } else {
      var child = left;
      while (child.isPresent()) {
        if (child.get().right.isEmpty()) break;
        child = child.get().right;
      }
      final LCRSNode<E> newRight = new LCRSNode<>(val);
      child.get().right = of(newRight);
      return newRight;
    }
  }


  public int size() {
    return 1 + left.map(LCRSNode::size).orElse(0) + right.map(LCRSNode::size).orElse(0);
  }
}
