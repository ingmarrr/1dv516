package src;

import java.util.Optional;

public class DLList<T> {

  private Optional<LLNode<T>> root;
  private Optional<LLNode<T>> end;

  public DLList(T root)  {
    this.root = root;
  }

  public boolean isEmpty() {
    return root.isEmpty();
  }

  public void addFirst(LLNode<T> node) {

  }

  public void addLast(LLNode<T> node) {

  }

  public LLNode<T> remFirst() {

  }

  public LLNode<T> remLast() {

  }
}
