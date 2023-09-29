package core;

import java.util.Optional;

import static java.util.Optional.empty;

public class LLNode<T> {

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

