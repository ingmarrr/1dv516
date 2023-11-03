package core.Problem6;


import java.util.Optional;

import static java.util.Optional.empty;

public class AVLNode<E extends Comparable<E>>  {
  private final E val;
  public Optional<AVLNode<E>> left;
  public Optional<AVLNode<E>> right;
  public int height;

  public E getVal() {
    return val;
  }

  public AVLNode(E val) {
    this.val = val;
    left = empty();
    right = empty();
    height = 1;
  }

  public int diff() {
    int lHeight = left.isPresent() ? left.get().height : 0;
    int rHeight = right.isPresent() ? right.get().height : 0;
    return lHeight - rHeight;
  }

  @Override
  public String toString() {
    return "(Val: " + val.toString() + " | Left: " + left.map(l -> l.toString()).orElse("None") + " | Right: " + right.map(r -> r.toString()).orElse("None") + " | Height: " + height + ")";
  }

}
