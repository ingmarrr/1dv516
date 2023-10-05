package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Stack<E> {
  private final List<E> elems;

  public Stack() {
    elems = new ArrayList<>();
  }

  public void push(E el) {
    elems.add(el);
  }

  public Optional<E> pop() {
    if (elems.size() == 0) return empty();
    return of(elems.remove(elems.size() - 1));
  }

  public Optional<E> peek() {
    if (elems.size() == 0) return empty();
    return of(elems.get(elems.size() - 1));
  }

}
