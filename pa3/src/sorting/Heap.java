package sorting;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static range.Range.range;

public class Heap<E extends Comparable<E>> implements Iterable<E> {

  private final static int CAP = 15;
  private int size;
  private final int d;
  private E[] bt;

  public Heap(int d, List<E> elems) {
    this.d = d;
    bt = (E[]) new Comparable[elems.size() + 1];
    size = elems.size();
    for (int i : range(elems.size())) {
      bt[i + 1] = elems.get(i);
    }
  }

  public void sort() {
    int upper = size / d;
    while (upper >= 1) sink(upper--);
    while (size > 1) {
      var tmp = bt[1];
      bt[1] = bt[size];
      bt[size] = tmp;
      size--;
      sink(1);
    }
  }

  public Optional<E> delMax() {
    if (size > 0) {
      E max = bt[1];

      bt[1] = bt[size];
      bt[size] = max;

      size--;
      sink(1);
      return of(max);
    }
    return empty();
  }

  private void sink(int valx) {
    while (valx * d <= size) {
      int childix = valx * d;
      if (childix < size && bt[childix].compareTo(bt[childix + 1]) < 0) childix++;
      if (!(bt[valx].compareTo(bt[childix]) < 0)) break;
      final E tmp = bt[valx];
      bt[valx] = bt[childix];
      bt[childix] = tmp;
      valx = childix;
    }
  }

  public void print() {
    for (int i : range(size + 1)) {
      System.out.println(bt[i]);
    }
  }

  @Override
  public Iterator<E> iterator() {
    return new InorderIterator();
  }

  private class InorderIterator implements Iterator<E> {
    int ix = 0;

    @Override
    public boolean hasNext() {
      return ix < bt.length - 1;
    }

    @Override
    public E next() {
      return bt[++ix];
    }
  }
}
