package core.Problem4;

import core.BTNode;
import core.Stack;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class BSTree<E extends Comparable<E>> implements Iterable<E> {

  private IterType iterType = IterType.InOrder;
  private Optional<BTNode<E>> root;
  private int size;

  public enum IterType { InOrder, PostOrder, PreOrder };

  public BSTree() {
    root = empty();
  }

  public int size() {
    return size(root);
  }

  private int size(Optional<BTNode<E>> node) {
    if (node.isEmpty()) return 0;
    return size(node.get().left) + size(node.get().right) + 1;
  }

  public void setIterType(IterType type) {
    iterType = type;
  }

  public BSTree<E> withIterType(IterType type) {
    iterType = type;
    return this;
  }

  public int height() {
    return height(root);
  }

  private int height(Optional<BTNode<E>> node) {
    if (node.isEmpty()) return -1;
    var hleft = height(node.get().left);
    var hright = height(node.get().right);
    return 1 + Math.max(hleft, hright);
  }

  public void remove(E val) {
    remove(val, root);
  }

  private void remove(E val, Optional<BTNode<E>> node) {
    if (node.isEmpty()) return;
    final BTNode<E> no = node.get();
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

  private boolean contains(E val, Optional<BTNode<E>> node) {
    if (node.isEmpty()) return false;
    final BTNode<E> no = node.get();
    return switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> contains(val, no.left);
      case -1 -> contains(val, no.right);
      default -> true;
    };
  }

  public void del(E val) {
    root = del(val, root);
  }

  private Optional<BTNode<E>> del(E val, Optional<BTNode<E>> node) {
    if (node.isEmpty()) return node;
    var no = node.get();

    switch (Integer.signum(no.getVal().compareTo(val))) {
      case 1 -> no.left = del(val, no.left);
      case -1 -> no.right = del(val, no.right);
      case 0 -> {
        if (no.left.isEmpty()) return no.right;
        if (no.right.isEmpty()) return no.left;
        node = min(no.right);
        node.get().right = delMin(no.right);
        node.get().left = no.left;
        return node;
      }
    }
    return of(no);
  }

  private Optional<BTNode<E>> min(Optional<BTNode<E>> node) {
    if (node.get().left.isEmpty()) return node;
    return min(node.get().left);
  }

  private Optional<BTNode<E>> delMin(Optional<BTNode<E>> node) {
    if (node.get().left.isEmpty()) return node.get().right;
    node.get().left = delMin(node.get().left);
    return node;
  }

  public void add(E val) {
    size++;
    if (root.isEmpty()) {
      root = of(new BTNode<>(val));
    } else {
      add(val, root.get());
    }
  }

  private void add(E val, BTNode<E> node) {
    switch (Integer.signum(node.getVal().compareTo(val))) {
      case 1 -> {
        if (node.left.isEmpty()) {
          node.left = of(new BTNode<>(val));
        } else {
          add(val, node.left.get());
        }
      }
      case -1 -> {
        if (node.right.isEmpty()) {
          node.right = of(new BTNode<>(val));
        } else {
          add(val, node.right.get());
        }
      }
    };
  }

  public Optional<E> kth(int k) {
    if (k >= size || k < 1) return empty();
    int cnt = 1;
    Optional<BTNode<E>> bigger = empty();
    Optional<E> out = empty();
    for (Iterator<BTNode<E>> it = new PostOrderBSTNodeIterator(); it.hasNext();) {
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

  private void toInOrder(List<E> acc, Optional<BTNode<E>> node) {
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

  private void toPreOrder(List<E> acc, Optional<BTNode<E>> node) {
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

  private void toPostOrder(List<E> acc, Optional<BTNode<E>> node) {
    if (node.isPresent()) {
      toPostOrder(acc, node.get().left);
      toPostOrder(acc, node.get().right);
      acc.add(node.get().getVal());
    }
  }


  public BSTInOrderIterator inOrder() {
    return new BSTInOrderIterator();
  }
  public BSTPreOrderIterator preOrder() {
    return new BSTPreOrderIterator();
  }

  public BSTPostOrderIterator postOrder() {
    return new BSTPostOrderIterator();
  }

  @Override
  public Iterator<E> iterator() {
    return switch(iterType) {
      case InOrder -> new BSTInOrderIterator();
      case PostOrder -> new BSTPostOrderIterator();
      case PreOrder -> new BSTPreOrderIterator();
    };
  }

  private static <T> Optional<T> check(Supplier<T> supp) {
    try {
      return of(supp.get());
    } catch (Exception E) {
      return empty();
    }
  }

  private final class BSTInOrderIterator implements Iterator<E> {
    final core.Stack<BTNode<E>> toHandle = new core.Stack<>();
    boolean didPop = false;
    Optional<BTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }


    @Override
    public E next() {
      if (didPop) {
        var node = cur.get();
        if (node.right.isEmpty()) {
          cur = toHandle.pop();
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
        cur = toHandle.pop();
        didPop = true;
        return node.getVal();
      }
      cur = node.right;
      return node.getVal();
    }
  }

  private final class BSTPreOrderIterator implements Iterator<E> {
    final core.Stack<BTNode<E>> toHandle = new core.Stack<>();
    boolean shouldPop = false;
    Optional<BTNode<E>> cur = root;

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
        cur = toHandle.pop();
        return out;
    }
  }
  private final class BSTPostOrderIterator implements Iterator<E> {
    final core.Stack<BTNode<E>> toHandle = new core.Stack<>();
    boolean didPop = false;
    Optional<BTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }

    @Override
    public E next() {
      if (didPop) {
        var node = cur.get();
        if (node.left.isEmpty()) {
          cur = toHandle.pop();
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
        cur = toHandle.pop();
        didPop = true;
        return node.getVal();
      }
      cur = node.left;
      return node.getVal();
    }
  }

  private final class PostOrderBSTNodeIterator implements Iterator<BTNode<E>> {
    final core.Stack<BTNode<E>> toHandle = new Stack<>();
    boolean didPop = false;
    Optional<BTNode<E>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.isPresent();
    }

    @Override
    public BTNode<E> next() {
      if (didPop) {
        var node = cur.get();
        if (node.left.isEmpty()) {
          cur = toHandle.pop();
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
        cur = toHandle.pop();
        didPop = true;
        return node;
      }
      cur = node.left;
      return node;
    }
  }

}
