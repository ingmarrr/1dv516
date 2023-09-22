package linkedlist;

import java.util.Optional;

public class DLList<T> {

  private Optional<LLNode<T>> root;
  private Optional<LLNode<T>> end;
  private int size;

  public DLList() {
    root = Optional.empty();
    end = Optional.empty();
    size = 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(T val) {
    final LLNode<T> node = new LLNode<>(val);
    size++;
    if (root.isEmpty()) {
      root = Optional.of(node);
      end = Optional.of(node);
      return;
    }
    final LLNode<T> oldRoot = root.get();
    oldRoot.prev = Optional.of(node);
    node.next = Optional.of(oldRoot);
    root = Optional.of(node);
  }

  public void addLast(T val) {
    final LLNode<T> node = new LLNode<>(val);
    size++;
    if (end.isEmpty()) {
      root = Optional.of(node);
      end = Optional.of(node);
      return;
    }

    final LLNode<T> oldEnd = end.get();
    oldEnd.next = Optional.of(node);
    node.prev = Optional.of(oldEnd);
    end = Optional.of(node);
  }

  public Optional<T> remFirst() {
    if (root.isEmpty()) {
      return Optional.empty();
    }

    final LLNode<T> tmpRoot = root.get();
    if (tmpRoot.next.isEmpty()) {
      root = Optional.empty();
      end = Optional.empty();
      return Optional.of(tmpRoot.getVal());
    }

    final LLNode<T> next = tmpRoot.next.get();
    next.prev = Optional.empty();
    root = Optional.of(next);
    size--;
    return Optional.of(tmpRoot.getVal());
  }

  public Optional<T> remLast() {
    if (end.isEmpty()) {
      return Optional.empty();
    }

    final LLNode<T> tmpEnd = end.get();
    if (tmpEnd.prev.isEmpty()) {
      root = Optional.empty();
      end = Optional.empty();
      return Optional.of(tmpEnd.getVal());
    }

    final LLNode<T> prev = tmpEnd.prev.get();
    prev.next = Optional.empty();
    end = Optional.of(prev);
    size--;
    return Optional.of(tmpEnd.getVal());
  }
}
