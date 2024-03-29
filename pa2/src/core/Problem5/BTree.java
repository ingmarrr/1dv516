package core.Problem5;

import core.BTNode;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BTree<E extends Comparable<E>> {

  private Optional<BTNode<E>> root;

  public BTree() {
    root = empty();
  }

//  public void add(E val) {
//    if (root.isEmpty()) {
//      root = of(new BTNode<>(val));
//    } else {
//      add(val, root);
//    }
//  }

  public void add(E val) {
    root = add(val, root);
  }

  private Optional<BTNode<E>> add(E val, Optional<BTNode<E>> node) {
    if (node.isEmpty()) return of(new BTNode<>(val));
    var no = node.get();

    switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> no.left = add(val, no.left);
      case -1 -> no.right = add(val, no.right);
    }

    return of(no);
  }


//  private void add(E val, Optional<BTNode<E>> node) {
//    if (node.isEmpty()) {
//      node = of(new BTNode<>(val));
//      return;
//    }
//
//    Queue<BTNode<E>> toCheck = new ArrayDeque<>();
//    toCheck.add(root.get());
//    while (!toCheck.isEmpty()) {
//      var tmp = toCheck.poll();
//      if (tmp.left.isEmpty()) {
//        tmp.left = of(new BTNode<>(val));
//        break;
//      } else {
//        toCheck.add(tmp.left.get());
//      }
//      if (tmp.right.isEmpty()) {
//        tmp.right = of(new BTNode<>(val));
//        break;
//      } else {
//        toCheck.add(tmp.right.get());
//      }
//    }
//  }

  public void printInorder() {
    printInorder(root);
  }

  private void printInorder(Optional<BTNode<E>> node) {
    if (node.isEmpty()) return;
    printInorder(node.get().left);
    System.out.print(node.get().getVal() + " ");
    printInorder(node.get().right);
  }

  public void printLevelorder() {
    printLevelorder(root);
  }

  private void printLevelorder(Optional<BTNode<E>> node) {
    if (node.isEmpty()) return;
    System.out.println(node.get().getVal().toString());

    System.out.println(node.get().left.map(l -> l.getVal().toString()).orElse(""));
    System.out.println(node.get().right.map(r -> r.getVal().toString()).orElse(""));
  }

  public boolean isIso(BTree<E> other) {
    return isIso(root, other.getRoot());
  }

  private boolean isIso(Optional<BTNode<E>> node, Optional<BTNode<E>> other) {
    if (node.isEmpty() && other.isEmpty()) return true;
    if (node.isEmpty() || other.isEmpty()) return false;
    if (!node.get().getVal().equals(other.get().getVal())) return false;
    return ((isIso(node.get().left, other.get().left) && isIso(node.get().right, other.get().right)) ||
        (isIso(node.get().left, other.get().right) && isIso(node.get().right, other.get().left)));
  }

  public Optional<BTNode<E>> getRoot() {
    return root;
  }
}
