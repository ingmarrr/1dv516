package linkedlist;

import java.util.*;

public class SPRandDeq<E> implements Iterable<E> {

  private final List<E> elems;
  private final Random rand = new Random();

  public SPRandDeq() {
    elems = new ArrayList<>();
  }

  public SPRandDeq(int reserve) {
    elems = new ArrayList<>(reserve);
  }

  public int size() {
    return elems.size();
  }

  public int last() {
    return elems.size() - 1;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public List<E> getElems() {
    return elems;
  }

  public void enqueue(E elem) throws NullPointerException {
    if (elem == null) {
      throw new NullPointerException();
    }

    elems.add(elem);
  }

  public Optional<E> dequeue() {
    if (size() == 0) {
      return Optional.empty();
    }
    final int choice = rand.nextInt(size());
    final E out = elems.get(choice);
    elems.set(choice, elems.get(last()));
    elems.remove(last());
    return Optional.of(out);
  }

  @Override
  public Iterator<E> iterator() {
    return new SPRandDeqIterator();
  }

  private class SPRandDeqIterator implements Iterator<E> {
    @Override
    public boolean hasNext() {
      return size() > 0;
    }

    @Override
    public E next() {
      return dequeue().get();
    }
  }
}
