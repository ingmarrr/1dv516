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

  public int size() {
    return size;
  }

  public int height() {
    return height(0, root) - 1;
  }

  private int height(int max, Optional<BSTNode<E>> node) {
    if (node.isEmpty()) return 0;

    var hleft = height(0, node.get().left);
    var hright = height(0, node.get().right);

    return 1 + max + Math.max(hleft, hright);
  }

  public void remove(E val) {
    remove(val, root);
  }

  private void remove(E val, Optional<BSTNode<E>> node) {
    if (node.isEmpty()) return;
    final BSTNode<E> no = node.get();
    switch (Integer.signum(no.getVal().compareTo(val))) {
      case -1 -> no.right.ifPresent(right -> {
          if (right.getVal().equals(val)) {
            no.right = right.right.isPresent() ? right.right : right.left;
          } else {
            remove(val, no.right);
          }
      });
      case 1 -> no.left.ifPresent(left -> {
          if (left.getVal().equals(val)) {
            no.left = left.right.isPresent() ? left.right : left.left;
          } else {
            remove(val, no.left);
          }
      });
      case 0 -> node = no.right.or(() -> no.left);
    }
    size--;
  }

  public boolean contains(E val) {
    return contains(val, root);
  }

  private boolean contains(E val, Optional<BSTNode<E>> node) {
    if (node.isEmpty()) return false;
    final BSTNode<E> no = node.get();
    System.out.println(Integer.signum(no.getVal().compareTo(val)));
    return switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> contains(val, no.left);
      case -1 -> contains(val, no.right);
      default -> true;
    };
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
    if (k >= size || k < 1) return empty();
    int cnt = 1;
    Optional<BSTNode<E>> bigger = empty();
    Optional<E> out = empty();
    for (Iterator<BSTNode<E>> it = new PostOrderBSTNodeIterator(); it.hasNext();) {
      var i = it.next();
      if (cnt == k - 1) {
        bigger = of(i);
      }
      if (cnt == k) {
        out = of(i.getVal());
      }
      if (cnt == k + 1) {
        size--;
        i.right = bigger;
        break;
      }
      cnt++;
    }
    return out;
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

  private final class PostOrderBSTNodeIterator implements Iterator<BSTNode<E>> {
    final Stack<BSTNode<E>> toHandle = new Stack<>();
    boolean didPop = false;
    Optional<BSTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }

    @Override
    public BSTNode<E> next() {
      if (didPop) {
        var node = cur.get();
        if (node.left.isEmpty()) {
          cur = check(() -> toHandle.pop());
          return node;
        }
        cur = node.left;
        didPop = false;
        return node;
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
        return node;
      }
      cur = node.left;
      return node;
    }
  }

}
