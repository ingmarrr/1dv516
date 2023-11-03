package core.Problem2;

import core.Stack;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class SPRandDeque<E> implements Iterable<E> {

  private E[] elems;
  private int cnt;
  private int len;
  private final Random rand = new Random();

  public SPRandDeque() {
    this(0);
  }

  @SuppressWarnings("unchecked")
  public SPRandDeque(int reserve) {
    elems = (E[]) new Object[reserve];
    cnt = 0;
    len = reserve;
  }

  private SPRandDeque(E[] elems, int cnt, int len) {
    this.elems = elems;
    this.cnt = cnt;
    this.len = len;
  }

  public int size() {
    return cnt;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public E[] getElems() {
    return elems.clone();
  }

  public void enqueue(E elem) throws NullPointerException {
    if (elem == null) throw new NullPointerException();
    if (cnt == len) extend();

    elems[cnt] = elem;
    cnt++;
  }

  public Optional<E> dequeue() {
    if (cnt == 0) return empty();
    final int choice = rand.nextInt(cnt);
    final E out = swap(choice, cnt - 1);
    cnt--;
    return of(out);
  }

  @SuppressWarnings("unchecked")
  private void extend() {
    if (len == 0) len++;
    len *= 2;
    E[] resizedElems = (E[]) new Object[len];
    System.arraycopy(elems, 0, resizedElems, 0, cnt);
    elems = resizedElems;
  }

  private E swap(int first, int second) {
    final E tmp = elems[first];
    elems[first] = elems[second];
    elems[second] = tmp;
    return tmp;
  }

  public SPRandDeque<E> clone() {
    return new SPRandDeque<>(
        elems, cnt, len
    );
  }

  @Override
  public Iterator<E> iterator() {
    return new SPRandDeqIterator(clone());
  }

  private class SPRandDeqIterator implements Iterator<E> {
    final SPRandDeque<E> iterDeque;

    public SPRandDeqIterator(SPRandDeque<E> randDequeue) {
      iterDeque = randDequeue;
    }

    @Override
    public boolean hasNext() {
      return !iterDeque.isEmpty();
    }

    @Override
    public E next() {
      return iterDeque.dequeue().get();
    }
  }
}
