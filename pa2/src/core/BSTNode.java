package core;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.*;

public class BSTNode<E extends Comparable<E>> {
  private final E val;
  public Optional<BSTNode<E>> left;
  public Optional<BSTNode<E>> right;

  public E getVal() {
    return val;
  }

  public BSTNode(E val) {
    this.val = val;
    left = empty();
    right = empty();
  }

  public void add(E ins) {
//    System.out.println("Val : " +  val + " | Ins: " + ins + " | Signum: " + Integer.signum(val.compareTo(ins)));
    switch (Integer.signum(val.compareTo(ins))) {
      case 1 -> addLeft(ins);
      case -1 -> addRight(ins);
    };
  }

  private void addLeft(E ins) {
    if (left.isEmpty()) left = of(new BSTNode<>(ins));
    else left.get().add(ins);
  }

  private void addRight(E ins) {
    if (right.isPresent()) right.get().add(ins);
    else right = of(new BSTNode<>(ins));
  }

}
