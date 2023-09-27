package ftree;

import java.util.Optional;

public class LCRSNode<T> {
  private final T val;
  private Optional<LCRSNode<T>> left;
  private Optional<LCRSNode<T>> right;

  public LCRSNode(T value) {
    val = value;
  }

  public void walk() {
    System.out.println(val);
    left.ifPresent(LCRSNode::walk);
    right.ifPresent(LCRSNode::walk);
  }

  public int degree() {
    int deg = 0;
    var child = left;
    while (child.isPresent()) {
      deg++;
      child = child.get().right;
    }
    return deg;
  }

  public LCRSNode<T> add(T val) {
    if (left.isEmpty()) {
      final LCRSNode<T> newLeft = new LCRSNode<>(val);
      left = Optional.of(newLeft);
      return newLeft;
    } else {
      var child = left;
      while (child.isPresent()) {
        child = child.get().right;
      }
      final LCRSNode<T> newRight = new LCRSNode<>(val);
      child.get().right = Optional.of(newRight);
      return newRight;
    }
  }
}
