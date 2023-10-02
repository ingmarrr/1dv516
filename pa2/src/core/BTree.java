package core;

import java.util.Optional;

public class BTree<E extends Comparable<E>> {

  public boolean isIso(Optional<BTNode<E>> node, Optional<BTNode<E>> other) {
    if (node.isEmpty() && other.isEmpty()) return true;
    if (node.isEmpty() || other.isEmpty()) return false;
    if (node.get().getVal().compareTo(other.get().getVal()) != 0) return false;
    return false;
  }
}
