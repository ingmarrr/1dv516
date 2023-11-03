package core.Problem1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Deque<T> implements Iterable<T> {

  private Optional<LLNode<T>> root;
  private Optional<LLNode<T>> end;
  private int size;

  public Deque() {
    root = empty();
    end = empty();
    size = 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(T val) {
    final LLNode<T> node = new LLNode<>(val);
    if (emptyInit(node)) {
      return;
    }
    final LLNode<T> oldRoot = root.get();
    oldRoot.prev = of(node);
    node.next = of(oldRoot);
    root = of(node);
  }

  public void addLast(T val) {
    final LLNode<T> node = new LLNode<>(val);
    if (emptyInit(node)) {
      return;
    }

    final LLNode<T> oldEnd = end.get();
    oldEnd.next = of(node);
    node.prev = of(oldEnd);
    end = of(node);
  }

  private boolean emptyInit(LLNode<T> node) {
    size++;
    if (root.isEmpty()) {
      root = of(node);
      end = of(node);
      return true;
    }
    return false;
  }

  public Optional<T> remFirst() {
    if (root.isEmpty()) {
      return empty();
    }

    final LLNode<T> tmpRoot = root.get();
    if (tmpRoot.next.isEmpty()) {
      root = empty();
      end = empty();
      return of(tmpRoot.getVal());
    }

    final LLNode<T> next = tmpRoot.next.get();
    next.prev = empty();
    root = of(next);
    size--;
    return of(tmpRoot.getVal());
  }

  public Optional<T> remLast() {
    if (end.isEmpty()) {
      return empty();
    }

    final LLNode<T> tmpEnd = end.get();
    if (tmpEnd.prev.isEmpty()) {
      root = empty();
      end = empty();
      return of(tmpEnd.getVal());
    }

    final LLNode<T> prev = tmpEnd.prev.get();
    prev.next = empty();
    end = of(prev);
    size--;
    return of(tmpEnd.getVal());
  }

  @Override
  public Iterator<T> iterator() {
    return new DLLIterator();
  }

  private class DLLIterator implements Iterator<T> {
    private Optional<LLNode<T>> cur = root;

    @Override
    public boolean hasNext() {
      return cur.map(dllNode -> dllNode.next.isPresent()).orElse(false);
    }

    @Override
    public T next() throws NoSuchElementException {
      if (cur.isEmpty()) {
        throw new NoSuchElementException();
      }
      LLNode<T> curNode = cur.get();
      T data = curNode.getVal();
      cur = curNode.next;
      return data;
    }
  }

  public static class LLNode<T> {

    private T val;
    public Optional<LLNode<T>> next;
    public Optional<LLNode<T>> prev;

    public LLNode(T value) {
      val = value;
      next = empty();
      prev = empty();
    }

    public T getVal() {
      return val;
    }

    public void setVal(T val) {
      this.val = val;
    }
  }
}
