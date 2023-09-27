package linkedlist;

import java.util.*;

import static range.Range.range;

public class MemRandDeq<E> implements Iterable<E> {

  private final List<E> elems;
  private final List<Integer> ptrs;
  private int size;
  private final Random rand = new Random();

  public MemRandDeq() {
    elems = new ArrayList<>();
    ptrs = new ArrayList<>();
    size = 0;
  }

  public MemRandDeq(int reserve) {
    elems = new ArrayList<>(reserve);
    ptrs = new ArrayList<>(reserve);
    size = 0;
  }

  public int size() {
    return size;
  }

  public List<Integer> getPtrs() {
    return ptrs;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void enqueue(E elem) throws NullPointerException{
    if (elem == null) {
      throw new NullPointerException();
    }

    if (size == ptrs.size()) {
      ptrs.add(size);
      elems.add(elem);
      size++;
      return;
    }

    for (int px : range(ptrs.size())) {
      final int ptr = ptrs.get(px);

      if (ptr + 1 >= size - 1 || ptrs.get(px + 1) - ptr > 1) {
        ptrs.add(ptr + 1, ptr + 1);
        elems.add(ptr + 1, elem);
        size++;
        return;
      }
    }
  }

  public Optional<E> dequeue() {
    if (isEmpty()) {
      return Optional.empty();
    }

    final int choice = rand.nextInt(size);
    final E elem = elems.get(ptrs.get(choice));
    ptrs.remove(choice);
    size--;
    return Optional.of(elem);
  }

  @Override
  public Iterator<E> iterator() {
    return new MemRandDeqIterator();
  }


  private class MemRandDeqIterator implements Iterator<E> {
    @Override
    public boolean hasNext() {
      return size > 0;
    }

    @Override
    public E next() {
      return dequeue().get();
    }
  }
}
