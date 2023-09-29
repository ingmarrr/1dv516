package core;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BST<E extends Comparable<E>> implements Iterable<E> {
  private Optional<BSTNode<E>> root;
  private int size;

  public BST() {
    root = empty();
  }

  public int getSize() {
    return size;
  }

  public void add(E val) {
    size++;
    if (root.isEmpty()) {
      root = of(new BSTNode<>(val));
    } else {
      root.get().add(val);
    }
  }

  public Optional<E> kth(int k) {
    var cnt = 1;
    var iter = postOrder();
    for (Iterator<E> it = iter; it.hasNext();) {
      var i = it.next();
      if (cnt == k) {
        return of(i);
      }
      cnt++;
    }
    return empty();
  }

  public List<E> toInOrder() {
    final List<E> inOrder = new ArrayList<>();
    toInOrder(inOrder, root);
    return inOrder;
  }

  private void toInOrder(List<E> acc, Optional<BSTNode<E>> node) {
    if (node.isPresent()) {
      toInOrder(acc, node.get().left);
      acc.add(node.get().getVal());
      toInOrder(acc, node.get().right);
    }
  }

  public List<E> toPreOrder() {
    final List<E> inOrder = new ArrayList<>();
    toPreOrder(inOrder, root);
    return inOrder;
  }

  private void toPreOrder(List<E> acc, Optional<BSTNode<E>> node) {
    if (node.isPresent()) {
      acc.add(node.get().getVal());
      toPreOrder(acc, node.get().left);
      toPreOrder(acc, node.get().right);
    }
  }

  public List<E> toPostOrder() {
    final List<E> inOrder = new ArrayList<>();
    toInOrder(inOrder, root);
    return inOrder;
  }

  private void toPostOrder(List<E> acc, Optional<BSTNode<E>> node) {
    if (node.isPresent()) {
      toPostOrder(acc, node.get().left);
      toPostOrder(acc, node.get().right);
      acc.add(node.get().getVal());
    }
  }

  public Iterator<E> inOrder() {
    return new BSTInOrderIterator();
  }
  public Iterator<E> preOrder() {
    return new BSTPreOrderIterator();
  }

  public Iterator<E> postOrder() {
    return new BSTPostOrderIterator();
  }

  @Override
  public Iterator<E> iterator() {
    return new BSTInOrderIterator();
  }

  private static <T> Optional<T> check(Supplier<T> supp) {
    try {
      return of(supp.get());
    } catch (Exception E) {
      return empty();
    }
  }

  private final class BSTInOrderIterator implements Iterator<E> {
    final Stack<BSTNode<E>> toHandle = new Stack<>();
    boolean didPop = false;
    Optional<BSTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }


    @Override
    public E next() {
      if (didPop) {
        var node = cur.get();
        if (node.right.isEmpty()) {
          cur = check(() -> toHandle.pop());
          return node.getVal();
        }
        cur = node.right;
        didPop = false;
        return node.getVal();
      }

      while (cur.get().left.isPresent()) {
        toHandle.push(cur.get());
        cur = cur.get().left;
      }

      var node = cur.get();
      if (node.right.isEmpty()) {
        cur = check(() -> toHandle.pop());
        didPop = true;
        return node.getVal();
      }
      cur = node.right;
      return node.getVal();
    }
  }
  private final class BSTPreOrderIterator implements Iterator<E> {
    final Stack<BSTNode<E>> toHandle = new Stack<>();
    boolean shouldPop = false;
    Optional<BSTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }

    @Override
    public E next() {
        var node = cur.get();
        var out = node.getVal();
        if (node.right.isPresent()) {
          toHandle.push(node.right.get());
        }
        if (node.left.isPresent()) {
          toHandle.push(node.left.get());
        }
        cur = check(() -> toHandle.pop());
        return out;

//      if (shouldPop) {
//        cur = of(toHandle.pop());
//      }
//      var node = cur.get();
//      var out = node.getVal();
//      if (node.right.isPresent()) {
//        toHandle.push(node.right.get());
//      }
//      if (node.left.isPresent()) {
//        toHandle.push(node.left.get());
//      }
//      shouldPop = true;
//      if (toHandle.size() == 0) {
//        cur = empty();
//      }
//      return out;
    }
  }
  private final class BSTPostOrderIterator implements Iterator<E> {
    final Stack<BSTNode<E>> toHandle = new Stack<>();
    boolean didPop = false;
    Optional<BSTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }

    @Override
    public E next() {
      if (didPop) {
        var node = cur.get();
        if (node.left.isEmpty()) {
          cur = check(() -> toHandle.pop());
          return node.getVal();
        }
        cur = node.left;
        didPop = false;
        return node.getVal();
      }

      while (cur.get().right.isPresent()) {
        toHandle.push(cur.get());
        cur = cur.get().right;
      }

      if (cur.get().left.isPresent()) {
        var node = cur.get();
      }

      var node = cur.get();
      if (node.left.isEmpty()) {
        cur = check(() -> toHandle.pop());
        didPop = true;
        return node.getVal();
      }
      cur = node.left;
      return node.getVal();
    }
  }

}
