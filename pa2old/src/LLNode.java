package src;

import java.util.Optional;

public class LLNode<T> {

  private T val;
  public Optional<LLNode<T>> next;
  public Optional<LLNode<T>> prev;

  public LLNode(T value) {
    val = value;
    next = Optional.empty();
    prev = Optional.empty();
  }
}

