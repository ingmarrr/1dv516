package core;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class MemRandDeq<E> implements Iterable<E> {

  private E[] elems;
  private int[] ptrs;

  private int cnt;
  private int len;
  private final Random rand = new Random();

  @SuppressWarnings("unchecked")
  public MemRandDeq(int reserve) {
    if (reserve < 0) reserve = 0;
    elems = (E[]) new Object[reserve];
    ptrs = new int[reserve];
    fillPtrs();
    cnt = 0;
    len = reserve;
  }

  public MemRandDeq() {
    this(0);
  }

  @SuppressWarnings("unchecked")
  private void extend() {
    if (len == 0) len++;
    len *= 2;
    E[] resizedElems = (E[]) new Object[len];
    int[] resizedPtrs = new int[len];
    System.arraycopy(elems, 0, resizedElems, 0, cnt);
    System.arraycopy(ptrs, 0, resizedPtrs, 0, cnt);
    elems = resizedElems;
    ptrs = resizedPtrs;
    fillPtrs();
  }

  public void enqueue(E val) throws NullPointerException{
    if (val == null) throw new NullPointerException();
    if (cnt == len) extend();
    final int ptr = ptrs[cnt] == -1 ? cnt : ptrs[cnt];

    elems[ptr] = val;
    ptrs[ptr] = ptr;
    cnt++;
  }

  public Optional<E> dequeue() {
    if (cnt == 0) return empty();
    final int choice = rand.nextInt(cnt);
    swap(choice, cnt - 1);
    cnt--;
    return of(elems[ptrs[choice]]);
  }

  private void swap(int first, int second) {
    final int tmp = ptrs[first];
    ptrs[first] = ptrs[second];
    ptrs[second] = tmp;
  }

  private void fillPtrs() {
    for (final int i : range(cnt, len)) {
      ptrs[i] = -1;
    }
  }

  public int size() {
    return cnt;
  }

  public boolean isEmpty() {
    return cnt == 0;
  }

  public int[] getPtrs() {
    return ptrs.clone();
  }

  public E[] getElems() {
    return elems.clone();
  }

  @Override
  public Iterator<E> iterator() {
    return new MemRandDeqIterator();
  }

  private final class MemRandDeqIterator implements Iterator<E> {
    @Override
    public boolean hasNext() {
      return cnt > 0;
    }

    @Override
    public E next() {
      return dequeue().get();
    }
  }
}
