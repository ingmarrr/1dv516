package core;

import java.util.Optional;

import static java.util.Optional.*;

public class BTNode<E extends Comparable<E>> {
  private final E val;
  public Optional<BTNode<E>> left;
  public Optional<BTNode<E>> right;

  public E getVal() {
    return val;
  }

  public BTNode(E val) {
    this.val = val;
    left = empty();
    right = empty();
  }

}
