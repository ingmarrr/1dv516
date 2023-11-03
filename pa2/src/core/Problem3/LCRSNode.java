package core.Problem3;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class LCRSNode<E extends Comparable<E>> {

  private String prefix;
  private final E val;
  private Optional<LCRSNode<E>> left;
  private Optional<LCRSNode<E>> right;

  public E getVal() {
    return val;
  }

  public LCRSNode(E val) {
    left = empty();
    right = empty();
    this.val = val;
    this.prefix = "";
  }
  public LCRSNode(E val, String prefix) {
    left = empty();
    right = empty();
    this.val = val;
    this.prefix = prefix;
  }

  public void print() {
    System.out.println(prefix + val);
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

  public LCRSNode<E> add(E val, String prefix) {
    if (left.isEmpty()) {
      final LCRSNode<E> newLeft = new LCRSNode<>(val, prefix);
      left = of(newLeft);
      return newLeft;
    } else {
      var child = left;
      while (child.isPresent()) {
        if (child.get().right.isEmpty()) break;
        child = child.get().right;
      }
      final LCRSNode<E> newRight = new LCRSNode<>(val, prefix);
      child.get().right = of(newRight);
      return newRight;
    }
  }

  public Optional<LCRSNode<E>> find(E val) {
    return find(val, of(this));
  }

  private Optional<LCRSNode<E>> find(E val, Optional<LCRSNode<E>> node) {
    if (node.isEmpty()) return empty();
    if (node.get().val.equals(val)) return node;
    if (node.get().left.isPresent()) {
      var child = find(val, node.get().left);
      if (child.isPresent()) return child;
    }
    return find(val, node.get().right);
  }

  public int size() {
    return 1 + left.map(LCRSNode::size).orElse(0) + right.map(LCRSNode::size).orElse(0);
  }
}
