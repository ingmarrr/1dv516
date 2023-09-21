package src;

public class LLNode<T> {

  private final T val;
  private LLNode<T> next;
  private LLNode<T> prev;

  public LLNode(T val) {
    this.val = val;
  }


}
